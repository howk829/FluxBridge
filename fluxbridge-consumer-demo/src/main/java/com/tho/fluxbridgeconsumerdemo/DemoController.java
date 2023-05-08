package com.tho.fluxbridgeconsumerdemo;

import com.tho.fluxbridgeconsumerdemo.dto.DataSpec;
import com.tho.fluxbridgeconsumerdemo.dto.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class DemoController {

    @Autowired
    RSocketRequester rSocketRequester;

//    @PostConstruct
//    void subscribe() throws InterruptedException {
//
//        var res = rSocketRequester
//
//                .route("subscribe")
//                .data(Message.builder().topic("register_user").build())
//                .retrieveFlux(DataSpec.class)
//                .doOnNext(n -> {
//                    // should be tommyho
//                    log.info("receiving register_user: {}", n);
//                })
//
//                .subscribe();
//    }

    @MessageMapping("hold")
    void hold() {

    }


}
