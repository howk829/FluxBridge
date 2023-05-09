package com.tho.fluxbridgebroker;


import com.tho.fluxbridge.common.dto.DataSpec;
import com.tho.fluxbridge.common.mq.FluxBus;
import com.tho.fluxbridge.common.repo.KeyValueStore;
import com.tho.fluxbridge.common.repo.KeyValueStoreImpl;
import com.tho.fluxbridge.common.spring.FluxBridgeConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@SpringBootApplication
public class FluxbridgeBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxbridgeBrokerApplication.class, args);
	}

	// key value store for topic index
	@Bean
	KeyValueStore keyValueStore(
			@Value("${application.unique.name}") String appName,
			@Value("${key.store.home.path}") String homePath) {
		return new KeyValueStoreImpl(appName, homePath);
	}

	@Bean
	protected FluxBus serviceBus(@Value("${service.bus.data.path}") String dataPath, @Value("${service.bus.block_size}") int blkSize) {
		return new FluxBus(dataPath, blkSize);
	}

	@Bean
	protected Sinks.Many<DataSpec> sink () {
		 return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
	}

}
