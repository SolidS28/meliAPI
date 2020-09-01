package com.mercadolibre.microitem.item.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mercadolibre.microitem.item.model.Child;
import com.mercadolibre.microitem.item.model.Item;
import com.mercadolibre.microitem.item.repository.ItemRepository;

import io.micrometer.core.annotation.Timed;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

	private static final ParameterizedTypeReference<List<Child>> listChildType = new ParameterizedTypeReference<List<Child>>() {
	};

	private final WebClient webClient;
	private final ItemRepository itemRepository;

	public ItemServiceImpl(WebClient webClient, ItemRepository itemRepository) {
		this.webClient = webClient;
		this.itemRepository = itemRepository;
	}
	
	public Mono<Item> getItem(String id) {
		Optional<Item> itemDb = itemRepository.findById(id);
			
		if (itemDb.isPresent()) {
			return Mono.just(itemDb.get());
		} else {
			return getItemFromRest(id);
		}
	}
	
	/**
	 * Merge the item and 
	 * @param id - item id	
	 * @return Mono reference of the merged item
	 */
	@Timed("API_REQUESTS")
	private Mono<Item> getItemFromRest(String id) {
		return fetchItem(id).zipWith(fetchChildren(id)).flatMap(tuple -> {
			tuple.getT1().setItems(tuple.getT2());
			try {
				itemRepository.save(tuple.getT1());
			} catch (DataIntegrityViolationException e) {
				// It was already added 
			}
			return Mono.just(tuple.getT1());
		}).onErrorResume(e -> Mono.empty());
	}

	/**
	 * Fetch the item according to its id
	 * @param id - item id
	 * @return Mono reference of the item
	 */
	private Mono<Item> fetchItem(String id) {
		return webClient.get().uri("https://api.mercadolibre.com/items/{id}", id).retrieve().bodyToMono(Item.class);
	}

	/**
	 * Fetch the children elements according to its parent's id
	 * @param id - parent's id
	 * @return Mono reference of the list of children
	 */
	private Mono<List<Child>> fetchChildren(String id) {
		return webClient.get().uri("https://api.mercadolibre.com/items/{id}/children", id).retrieve().bodyToMono(listChildType);
	}

}
