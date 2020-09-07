package com.mercadolibre.persistance.service;

import com.mercadolibre.microitem.item.model.Item;

public interface PersistanceService {
	
	/**
	 * Persistes an item
	 * @param item
	 */
	void saveItem(Item item);
}
