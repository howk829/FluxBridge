package com.tho.fluxbridgeconsumerdemo;

import com.tho.fluxbridgeconsumerdemo.mq.FluxBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.util.function.Tuple2;

import java.util.Map;

@Slf4j
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    FluxBus fluxBus;

    @GetMapping("/test")
    public String testQueue() {

        fluxBus.publish("register", Map.of(), "tommyho", String.class);

        fluxBus.subscribe("register", null, true, String.class)
                .map(Tuple2::toString)
                .doOnNext(n -> log.info("receiving: {}", n))
                .subscribe();
        return "finished";
    }

}
