package com.tho.fluxbridgebroker;

import com.tho.fluxbridgebroker.dto.DataSpec;
import com.tho.fluxbridgebroker.dto.Message;
import com.tho.fluxbridgebroker.mq.FluxBus;
import com.tho.fluxbridgebroker.repo.KeyValueStore;
import lombok.extern.slf4j.Slf4j;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
@Slf4j
public class FluxbridgeController {

    // TODO: a routing table record the last index of each topic (use chronicle map)


    @Autowired
    FluxBus fluxBus;

    @Autowired
    KeyValueStore keyValueStore;

    @PostConstruct
    void postConstruct() {

//        long val = keyValueStore.getOrDef("abc", -1);
//        log.info("val, {}", val);
//
//        var queue_out = ChronicleQueue
//                .singleBuilder("E:\\temp\\register_user").blockSize(65536).build();
//
//        log.info("queue_out {}", queue_out);


    }


    @MessageMapping("publish")
    public Mono<String> publish(@Headers Map<String, Object> metadata, @Payload Message<String> message) {


        // empty metadata
        long lastIndex = fluxBus.publish(message.getTopic(), Map.of(), message.getData(), String.class);
        log.info("publish lastIndex: {}", lastIndex);
        //record index after publish
        keyValueStore.putValue(message.getTopic(), lastIndex);
        return Mono.just("published");
    }

    @MessageMapping("subscribe")
    public Flux<DataSpec> subscribe(@Headers Map<String, Object> metadata, @Payload Message<String> message) {
        // get topic last index

        log.info("subscribe: {}", message);
        long lastIndex = keyValueStore.getOrDef(message.getTopic(), -1);
        return fluxBus.subscribe(message.getTopic(), lastIndex == -1 ? null : lastIndex, true, String.class)
                .map(u -> DataSpec.builder().metadata(u.getT1()).message(u.getT2()).build());
//        return fluxBus.subscribe(message.getTopic(), lastIndex == -1 ? null: lastIndex, true, String.class)
//                .map(u -> DataSpec.builder().metadata(u.getT1()).message(u.getT2()).build());

    }
}
