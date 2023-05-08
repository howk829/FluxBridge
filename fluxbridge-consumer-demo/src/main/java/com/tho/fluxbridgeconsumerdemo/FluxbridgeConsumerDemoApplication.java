package com.tho.fluxbridgeconsumerdemo;

import com.tho.fluxbridgeconsumerdemo.mq.FluxBus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

@SpringBootApplication
public class FluxbridgeConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxbridgeConsumerDemoApplication.class, args);
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

	@Bean
	protected FluxBus serviceBus(@Value("${service.bus.data.path}") String dataPath, @Value("${service.bus.block_size}") int blkSize) {
		return new FluxBus(dataPath, blkSize);
	}


}
