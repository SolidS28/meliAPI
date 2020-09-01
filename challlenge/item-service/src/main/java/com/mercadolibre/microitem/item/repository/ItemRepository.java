package com.mercadolibre.microitem.item.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.microitem.item.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {

}
