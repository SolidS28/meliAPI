package com.mercadolibre.microitem.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class CustomMetricConfig {

	@Bean
	public HttpTraceRepository htttpTraceRepositoryIncoming() {
		return new InMemoryHttpTraceRepository();
	}
	
}
