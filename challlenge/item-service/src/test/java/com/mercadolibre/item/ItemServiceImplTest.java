package com.mercadolibre.item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.web.reactive.function.client.WebClient;

import com.mercadolibre.microitem.item.repository.ItemRepository;
import com.mercadolibre.microitem.item.service.ItemService;
import com.mercadolibre.microitem.item.service.ItemServiceImpl;

public class ItemServiceImplTest {

	@Mock
	private WebClient webClientMock;
	
	@Mock
	private ItemRepository itemRepositoryMock;
	
	@Mock
	private CacheManager cacheManager;
	
	@Mock
	private RabbitTemplate rabbitTemplate;
	
	private ItemService itemService;
	
    @Before
    public void setupMock() {
       MockitoAnnotations.initMocks(this);
    }
    
    @Before
    public void init() {
    	this.itemService = new ItemServiceImpl(webClientMock, itemRepositoryMock, cacheManager, rabbitTemplate); 
    }

	@Test
	public void itemFromDatabase() {

		
		//assert(expecteds, actuals);
	}
	
	@Test
	public void itemFromDatabaseError() {
		
	}
	
	@Test
	public void itemFromRestApi() {
		
	}
	
	@Test
	public void itemDoesNotExist() {
		
	}
}
