package com.tho.fluxbridgebroker;


import com.tho.fluxbridge.common.dto.DataSpec;
import com.tho.fluxbridge.common.dto.Message;
import com.tho.fluxbridge.common.mq.FluxBus;
import com.tho.fluxbridge.common.repo.KeyValueStore;
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
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

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

    @Autowired
    Sinks.Many<DataSpec> s;

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
//        long lastIndex = fluxBus.publish(message.getTopic(), Map.of(), message.getData(), String.class);
//        log.info("publish lastIndex: {}", lastIndex);
//        //record index after publish
//        keyValueStore.putValue(message.getTopic(), lastIndex);

        s.emitNext(
                DataSpec.builder()
                        .message(message.getData())
                        .build(),
                Sinks.EmitFailureHandler.FAIL_FAST
        );

        return Mono.just("published");
    }

    @MessageMapping("subscribe")
    public Flux<DataSpec> subscribe(@Headers Map<String, Object> metadata, @Payload Message<String> message) {
        // get topic last index

        log.info("subscribe: {}", message);
        // problem: failed to send the subscribed data

        return s.asFlux();

        // return Flux.just(DataSpec.builder().message("hello").build());
        // long lastIndex = keyValueStore.getOrDef(message.getTopic(), -1);
//        return fluxBus.subscribe(message.getTopic(), null, true, String.class)
//                .map(u -> DataSpec.builder().metadata(u.getT1()).message(u.getT2()).build())
//                .concatWith(Flux.just(DataSpec.builder().message("hello").build()));

    }
}
