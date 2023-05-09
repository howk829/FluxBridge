package com.tho.fluxbridgeconsumerdemo;

import com.tho.fluxbridge.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

@Slf4j
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private RSocketRequester rSocketRequester;

    @GetMapping("/test")
    public String testQueue() {

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
                        .topic("register")
                        .data("tommyho")
                        .build()
                )
                .retrieveMono(String.class)
                .block();

        log.info("res: {}", res);
        return "finished";
    }

}
