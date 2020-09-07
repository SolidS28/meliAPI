package com.mercadolibre.persistance.service;

import org.springframework.stereotype.Service;

import com.mercadolibre.microitem.item.model.Item;
import com.mercadolibre.persistance.repository.ItemRepository;

@Service
public class PersistanceServiceImpl implements PersistanceService {


	private final ItemRepository itemRepository;

	public PersistanceServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
		
	public void saveItem(Item item) {
		itemRepository.save(item);
	}


}
