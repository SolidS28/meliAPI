package com.mercadolibre.microitem.health.service;

import java.util.List;

import com.mercadolibre.microitem.health.model.CustomHealth;

public interface HealthService {
	/**
	 * Returns a list with the historical Custom Health data
	 * @return list of data about request and response that passed through the system 
	 */
	public List<CustomHealth> getHealthData();
}
