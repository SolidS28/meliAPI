package com.mercadolibre.microitem.item.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private String categoryId;
	private long price;
	private ZonedDateTime startTime;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Child> items;

}
