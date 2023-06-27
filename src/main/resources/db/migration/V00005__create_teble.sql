CREATE TABLE discount (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          discount_percent VARCHAR(255),
                          created_at timestamp,
                          modified_at timestamp
);

CREATE TABLE shopping_session (
                                  id BIGINT PRIMARY KEY,
                                  total DOUBLE,
                                  created_at timestamp,
                                  modified_at timestamp,
                                  user_id BIGINT,
                                  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE cart_item (
                           id BIGINT PRIMARY KEY,
                           quantity BIGINT,
                           created_at timestamp,
                           modified_at timestamp,
                           session_id BIGINT,
                           product_id BIGINT,
                           FOREIGN KEY (session_id) REFERENCES shopping_session (id),
                           FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE payment_details (
                                 id BIGINT PRIMARY KEY,
                                 order_id BIGINT,
                                 amount BIGINT,
                                 provider VARCHAR(255),
                                 status VARCHAR(255),
                                 created_at timestamp,
                                 modified_at timestamp
);

CREATE TABLE order_details (
                               id BIGINT PRIMARY KEY,
                               total DOUBLE,
                               created_at timestamp,
                               modified_at timestamp,
                               user_id BIGINT,
                               payment_id BIGINT,
                               FOREIGN KEY (user_id) REFERENCES users (id),
                               FOREIGN KEY (payment_id) REFERENCES payment_details (id)
);

CREATE TABLE order_items (
                             id BIGINT PRIMARY KEY,
                             order_id BIGINT,
                             created_at timestamp,
                             modified_at timestamp,
                             product_id BIGINT,
                             order_details_id BIGINT,
                             FOREIGN KEY (product_id) REFERENCES products (id),
                             FOREIGN KEY (order_details_id) REFERENCES order_details (id)
);

ALTER TABLE products
  ADD COLUMN discount_id BIGINT;

ALTER TABLE products
    ADD FOREIGN KEY (discount_id) REFERENCES discount (id);
