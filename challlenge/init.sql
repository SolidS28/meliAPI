-- meli.children definition

-- Drop table

-- DROP TABLE meli.children;

CREATE TABLE meli.children (
	id varchar(255) NOT NULL,
	stop_time timestamp NULL,
	CONSTRAINT children_pkey PRIMARY KEY (id)
);


-- meli.items definition

-- Drop table

-- DROP TABLE meli.items;

CREATE TABLE meli.items (
	category_id varchar(255) NULL,
	price int8 NOT NULL,
	start_time timestamp NULL,
	title varchar(255) NULL,
	id varchar(255) NOT NULL,
	CONSTRAINT items_pkey PRIMARY KEY (id),
	CONSTRAINT fkb2npmi74yu2tx8e8kqqtsmowk FOREIGN KEY (id) REFERENCES children(id)
);


-- meli.items_items definition

-- Drop table

-- DROP TABLE meli.items_items;

CREATE TABLE meli.items_items (
	item_id varchar(255) NOT NULL,
	items_id varchar(255) NOT NULL,
	CONSTRAINT uk_pmngk3hdxxps3dqwhvd3yd33k UNIQUE (items_id),
	CONSTRAINT fk6qsaipoy71ell0mu4filpvnvj FOREIGN KEY (items_id) REFERENCES children(id),
	CONSTRAINT fkitih7gnhewqdurvhrj3pouyht FOREIGN KEY (item_id) REFERENCES items(id)
);