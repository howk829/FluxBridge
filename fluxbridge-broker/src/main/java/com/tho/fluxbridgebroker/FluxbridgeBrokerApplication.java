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
}
