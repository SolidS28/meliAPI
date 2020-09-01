/**
 * 
 */
package com.mercadolibre.microitem.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.microitem.exception.EntityNotFound;
import com.mercadolibre.microitem.item.model.Item;
import com.mercadolibre.microitem.item.service.ItemService;

import reactor.core.publisher.Mono;

/**
 * @author Nicolas Vivas
 *
 */
@RestController
@RequestMapping("/items")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/{id}")
	public Mono<Item> getItem(@PathVariable String id) {
		return this.itemService.getItem(id).switchIfEmpty(Mono.error(() -> new EntityNotFound(Item.class.toGenericString(), id)));
	}

}
