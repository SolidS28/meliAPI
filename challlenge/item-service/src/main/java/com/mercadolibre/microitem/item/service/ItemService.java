package com.mercadolibre.microitem.item.service;

import com.mercadolibre.microitem.item.model.Item;

import reactor.core.publisher.Mono;

public interface ItemService {
	
	/**
	 * Get the item according to its id
	 * @param id - Id of the item
	 * @return Mono reference of the item, it could be empty
	 */
	Mono<Item> getItem(String id);
}
