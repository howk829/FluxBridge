package com.tho.fluxbridgeproducerdemo;

import com.google.gson.Gson;
import com.tho.fluxbridge.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {

    @Autowired
    private RSocketRequester rSocketRequester;

    @GetMapping("/register")
    public String register() {

        Gson gson = new Gson();

        var res = rSocketRequester
                .route("publish")
                .data(Message
                        .builder()
                        .topic("register")
                        .data(gson.toJson(new User("tommyho")))
                        .build()
                )
                .retrieveMono(String.class)
                .block();

        log.info("res: {}", res);
        return "finished";
    }
}
