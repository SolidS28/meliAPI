package com.mercadolibre.persistance.reciever;

import org.springframework.stereotype.Component;

import com.mercadolibre.microitem.item.model.Item;
import com.mercadolibre.persistance.service.PersistanceService;

@Component
public class PersistanceReciever {
	
	private final PersistanceService persistanceService;
	
	public PersistanceReciever(PersistanceService persistanceService) {
		this.persistanceService = persistanceService;
	}
	
	public void receiveMessage(Item item) {
		persistanceService.saveItem(item);
	}
}
