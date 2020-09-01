package com.mercadolibre.microitem.health.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.microitem.health.model.CustomHealth;
import com.mercadolibre.microitem.health.service.HealthService;

@RestController
@RequestMapping("/health")
public class HealthController {
	
	private HealthService healthService;
	
	public HealthController(HealthService healthService) {
		super();
		this.healthService = healthService;
	}


	@GetMapping()
	public List<CustomHealth> getHealth() {
		return healthService.getHealthData();
	}
	
}
