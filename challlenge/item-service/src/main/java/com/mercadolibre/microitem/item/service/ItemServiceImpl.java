package com.mercadolibre.microitem.item.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mercadolibre.microitem.config.CacheConfig;
import com.mercadolibre.microitem.config.WebClientConfig;
import com.mercadolibre.microitem.item.model.Child;
import com.mercadolibre.microitem.item.model.Item;
import com.mercadolibre.microitem.item.repository.ItemRepository;

import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

	private static final String PERSISTANCE_ROUTING = "persistance.items.#";
	private static final String PERSISTANCE_TOPIC = "items-persistance";

	private static final ParameterizedTypeReference<List<Child>> LIST_CHILD_TYPE_REF = new ParameterizedTypeReference<List<Child>>() {
	};

	private final WebClient webClient;
	private final ItemRepository itemRepository;
	private final CacheManager cacheManager;
	private final RabbitTemplate rabbitTemplate;

	public ItemServiceImpl(WebClient webClient, ItemRepository itemRepository, CacheManager cacheManager,
			RabbitTemplate rabbitTemplate) {
		this.webClient = webClient;
		this.itemRepository = itemRepository;
		this.cacheManager = cacheManager;
		this.rabbitTemplate = rabbitTemplate;
	}

	public Mono<Item> getItem(String id) {
		Cache cache = cacheManager.getCache(CacheConfig.ITEMS_CACHE);
		if (cache.get(id) != null) {
			Optional<Item> itemDb = itemRepository.findById(id);

			if (itemDb.isPresent()) {
				return Mono.just(itemDb.get());
			} else {
				cache.evict(id);
			}
		}
		return getItemFromRest(id);

	}

	/**
	 * Merge the item and
	 * 
	 * @param id - item id
	 * @return Mono reference of the merged item
	 */
	private Mono<Item> getItemFromRest(String id) {
		return fetchItem(id).zipWith(fetchChildren(id)).flatMap(tuple -> {
			if (tuple.getT1() != null) {
				tuple.getT1().setItems(tuple.getT2());
				sendPersistanceMessage(tuple.getT1());
				cacheManager.getCache(CacheConfig.ITEMS_CACHE).put(id, true);
			}

			return Mono.just(tuple.getT1());
		}).onErrorResume(e -> Mono.empty());
	}

	/**
	 * Fetch the item according to its id
	 * 
	 * @param id - item id
	 * @return Mono reference of the item
	 */
	private Mono<Item> fetchItem(String id) {
		return webClient.get().uri("https://api.mercadolibre.com/items/{id}", id).retrieve().bodyToMono(Item.class)
				.retry(WebClientConfig.DEFAULT_MAX_RETRIES).onErrorResume(e -> Mono.empty());
	}

	/**
	 * Fetch the children elements according to its parent's id
	 * 
	 * @param id - parent's id
	 * @return Mono reference of the list of children
	 */
	@Retryable(maxAttempts = 5)
	private Mono<List<Child>> fetchChildren(String id) {
		return webClient.get().uri("https://api.mercadolibre.com/items/{id}/children", id).retrieve()
				.bodyToMono(LIST_CHILD_TYPE_REF).retry(WebClientConfig.DEFAULT_MAX_RETRIES)
				.onErrorResume(e -> Mono.empty());
	}

	/**
	 * Sends the message to persiste the item into db
	 * 
	 * @param item - item to be persisted
	 * @throws IOException
	 */
	private void sendPersistanceMessage(Item item) {
		rabbitTemplate.convertAndSend(PERSISTANCE_TOPIC, PERSISTANCE_ROUTING, item);
	}

}
