package com.mercadolibre.microitem.item.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.microitem.item.model.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, String> {

}

