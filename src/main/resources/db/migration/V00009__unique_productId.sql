
/*ALTER TABLE cart_item
ADD CONSTRAINT product_id UNIQUE (product_id);*/

/*ALTER TABLE products
    ADD image_data BLOB;*/

CREATE TABLE product_photos (
                                id BIGINT PRIMARY KEY,
                                photo_data LONGBLOB NOT NULL
);


ALTER TABLE products ADD COLUMN photo_id BIGINT;
ALTER TABLE products ADD CONSTRAINT fk_product_photo FOREIGN KEY (photo_id) REFERENCES product_photos (id);
