package com.tho.fluxbridge.common.mq;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import net.openhft.chronicle.threads.Pauser;
import net.openhft.chronicle.wire.DocumentContext;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FluxBus {

    protected String _dataPath;
    protected long _blkSize;

    public final static String METADATA_FIELD ="meta";
    public final static String DATA_FIELD = "data";
    public final static String INDEX_FIELD = "version";


    public FluxBus(String dataPath, long blkSizeInM) {
        _dataPath = dataPath;
        _blkSize = blkSizeInM;
    }

    public <T> long publish(@NonNull String topic, Map<String, Object> metadata, T object, Class<T> clz) {
        String fullPath = String.format("%s%s%s", _dataPath, File.separator, topic);
        ChronicleQueue q = SingleChronicleQueueBuilder.binary(fullPath)
                .blockSize(_blkSize).build();
        try {
            var appender = q.acquireAppender();
            DocumentContext dc = q.acquireAppender().writingDocument();
            try {
                dc.wire().write(METADATA_FIELD.toString()).object(Map.class, metadata);
                dc.wire().write(DATA_FIELD.toString()).object(clz, object);
            } finally {
                dc.close();
            }
            return appender.lastIndexAppended();
        } finally {
            if (q != null && !q.isClosed()) {
                q.close();
            }
        }
    }

    public Flux<Tuple2<Map<String, Object>, Map>> subscribe(final @NonNull String topic, Long lastIndex, boolean listenAtEnd) {
        return subscribe(topic, lastIndex, listenAtEnd, Map.class);
    }
    public <T> Flux<Tuple2<Map<String, Object>, T>> subscribe(final @NonNull String topic, Long lastIndex, boolean listenAtEnd, Class<T> t) {
        String fullPath = String.format("%s%s%s", _dataPath, File.separator, topic);
        var queue_out = SingleChronicleQueueBuilder.binary(fullPath)
                .blockSize(_blkSize).build();
        var reader = queue_out.createTailer();
        var pauser = Pauser.millis(1, 10);

        if (lastIndex != null) {
            reader.moveToIndex(lastIndex + 1);
        }

        return Flux.create(sink ->
        {
            try {
                while (!sink.isCancelled()) {
                    DocumentContext dc = reader.readingDocument();
                    try {
                        if (dc.isPresent()) {

                            Map content = dc.wire().read(METADATA_FIELD.toString()).object(Map.class);
                            Map<String, Object> meta = new HashMap<>(content);
                            meta.put(INDEX_FIELD.toString(), dc.index());
                            sink.next(Tuples.of(meta, dc.wire().read(DATA_FIELD.toString()).object(t)));

                        } else if (listenAtEnd) {
                            pauser.pause();
                        } else {
                            sink.complete();
                            break;
                        }
                    } catch (Exception e) {
                        log.error("Error reading queue", e);
                    } finally {
                        if (dc != null) {
                            dc.close();
                        }
                    }
                }
            } finally {
                if (queue_out != null && !queue_out.isClosed()) {
                    queue_out.close();
                }
            }
        });

    }
}
