package com.tho.fluxbridgebroker;

import com.tho.fluxbridgebroker.mq.FluxBus;
import com.tho.fluxbridgebroker.repo.KeyValueStore;
import com.tho.fluxbridgebroker.repo.KeyValueStoreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
