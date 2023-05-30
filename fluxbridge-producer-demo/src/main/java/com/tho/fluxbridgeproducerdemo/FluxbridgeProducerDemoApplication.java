package com.tho.fluxbridgeproducerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

@SpringBootApplication
public class FluxbridgeProducerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxbridgeProducerDemoApplication.class, args);
	}

	@Bean
	RSocketRequester rSocketRequester() {
		return RSocketRequester.builder()
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
	}
}
