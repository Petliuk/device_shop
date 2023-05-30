CREATE TABLE product_category (
                                  id BIGINT PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255),
                                  created_at timestamp,
                                  modified_at timestamp,
                                  deleted_at timestamp
);

CREATE TABLE product_inventory (
                                   id BIGINT PRIMARY KEY,
                                   quantity int,
                                   created_at timestamp,
                                   modified_at timestamp,
                                   deleted_at timestamp
);

CREATE TABLE products (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255),
                          SKU VARCHAR(255),
                          price decimal,
                          created_at timestamp,
                          modified_at timestamp,
                          deleted_at timestamp,
                          product_inventory_id BIGINT,
                          product_category_id  BIGINT,
                          FOREIGN KEY (product_inventory_id) REFERENCES product_inventory (id),
                          FOREIGN KEY (product_category_id) REFERENCES product_category (id)
);

