/*DROP TABLE cart_item;
*/


/*CREATE TABLE cart_item (
                           id BIGINT PRIMARY KEY,
                           quantity BIGINT NOT NULL ,
                           created_at timestamp,
                           modified_at timestamp,
                           session_id BIGINT,
                           product_id BIGINT,
                           FOREIGN KEY (session_id) REFERENCES shopping_session (id),
                           FOREIGN KEY (product_id) REFERENCES products (id)
);*/