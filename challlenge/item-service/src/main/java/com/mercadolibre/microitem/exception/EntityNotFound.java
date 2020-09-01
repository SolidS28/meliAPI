package com.mercadolibre.microitem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException {
	
	public EntityNotFound(String t, String id) {
		super(String.format("Entity of type '%s' and id '%s' was not found", t, id));
	}
}
