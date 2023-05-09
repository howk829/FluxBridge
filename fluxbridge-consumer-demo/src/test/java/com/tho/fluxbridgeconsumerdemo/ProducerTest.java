package com.tho.fluxbridgeconsumerdemo;


import com.tho.fluxbridge.common.dto.DataSpec;
import com.tho.fluxbridge.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

@Slf4j
public class ProducerTest {


    @Test
    void produce() {


        RSocketRequester rSocketRequester = RSocketRequester.builder()
                .rsocketStrategies(
                        RSocketStrategies.builder()

                                .decoder(new Jackson2JsonDecoder())
                                .encoder(new Jackson2JsonEncoder())
                                .metadataExtractorRegistry(metadataExtractorRegistry -> {
                                    // meta
                                })
                                .dataBufferFactory(new DefaultDataBufferFactory(true))
                                .build()
                )
                .tcp("localhost", 7002);


        var res = rSocketRequester
                .route("publish")
                .data(Message
                        .builder()
                        .topic("register_user")
                        .data("tommyho")
                        .build()
                )
                .retrieveMono(String.class)
                .block();

        log.info("res: {}", res);

    }

    @Test
    void subscribe() throws InterruptedException {



        RSocketRequester rSocketRequester = RSocketRequester.builder()
                .rsocketStrategies(
                        RSocketStrategies.builder()

                                .decoder(new Jackson2JsonDecoder())
                                .encoder(new Jackson2JsonEncoder())
                                .metadataExtractorRegistry(metadataExtractorRegistry -> {
                                    // meta
                                })
                                .dataBufferFactory(new DefaultDataBufferFactory(true))
                                .build()
                )
                .tcp("localhost", 7002);

        rSocketRequester
                .route("subscribe")
                .data(Message.builder().topic("register_user").build())
                .retrieveFlux(DataSpec.class)
                .doOnNext(n -> {
                    // should be tommyho
                    log.info("receiving register_user: {}", n);
                })
                .subscribe();

        Thread.sleep(Integer.MAX_VALUE);


    }


}
