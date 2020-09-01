package com.mercadolibre.microitem.item.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="children")
@Inheritance(strategy=InheritanceType.JOINED)
public class Child implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@JsonProperty("stop_time")
	private ZonedDateTime stopTime;
}
