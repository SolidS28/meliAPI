package com.mercadolibre.microitem.item.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends Child implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	@JsonProperty("category_id")
	private String categoryId;
	private long price;
	@JsonProperty("start_time")
	private ZonedDateTime startTime;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Child> items;

}
