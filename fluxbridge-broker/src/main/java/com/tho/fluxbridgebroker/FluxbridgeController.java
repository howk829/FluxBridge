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
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
public class FluxbridgeController {

    ConcurrentHashMap<String, Sinks.Many<DataSpec>> subscriberMap = new ConcurrentHashMap<>();

    @MessageMapping("publish")
    public Mono<String> publish(@Headers Map<String, Object> metadata, @Payload Message<String> message) {

        if (subscriberMap.containsKey(message.getTopic())) {

            subscriberMap.get(message.getTopic())
                    .emitNext(
                            DataSpec.builder()
                                    .data(message.getData())
                                    .build(),
                            Sinks.EmitFailureHandler.FAIL_FAST
                    );
        } else {
            return Mono.error(new IllegalArgumentException("topic not found"));
        }
        return Mono.just("published");
    }

    @MessageMapping("subscribe")
    public Flux<DataSpec> subscribe(@Headers Map<String, Object> metadata, @Payload String topic) {

        return subscriberMap.computeIfAbsent(topic, k ->
                Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false)
        ).asFlux();

    }
}
