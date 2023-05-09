package com.tho.fluxbridgeconsumerdemo;

import com.tho.fluxbridge.common.spring.FluxBridgeConfiguration;
import com.tho.fluxbridge.common.spring.FluxBusBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

import java.net.URISyntaxException;

@SpringBootApplication
@Import(FluxBridgeConfiguration.class)
public class FluxbridgeConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxbridgeConsumerDemoApplication.class, args);
	}


}
