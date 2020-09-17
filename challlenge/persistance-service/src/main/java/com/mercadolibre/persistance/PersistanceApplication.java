package com.mercadolibre.persistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.mercadolibre.microitem.item.model")
public class PersistanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistanceApplication.class, args);
	}

}
