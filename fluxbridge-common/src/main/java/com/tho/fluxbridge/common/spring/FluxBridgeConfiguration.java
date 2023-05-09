package com.tho.fluxbridge.common.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Configuration
public abstract class FluxBridgeConfiguration {


    @Value("${fluxbridge.host}")
    String fluxBridgeHost;

    @Value("${fluxbridge.port}")
    int fluxBridgePort;

    @Bean
    RSocketRequester rSocketRequester()  {
        return RSocketRequester.builder()

                .rsocketStrategies(
                        RSocketStrategies.builder()
                                .decoder(new Jackson2JsonDecoder())
                                .encoder(new Jackson2JsonEncoder())
                                .metadataExtractorRegistry(metadataExtractorRegistry -> {
                                    // meta
                                })
                                //.dataBufferFactory(new DefaultDataBufferFactory(true))
                                .build()
                )
                //.websocket(new URI("ws://localhost:7002"));
                .tcp(fluxBridgeHost, fluxBridgePort);
    }

    @Bean
    FluxBusBeanPostProcessor fluxBusBeanPostProcessor() {
        return new FluxBusBeanPostProcessor();
    }
}
