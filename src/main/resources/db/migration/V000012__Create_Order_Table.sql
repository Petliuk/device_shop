
CREATE TABLE order_in_admin (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                user_id BIGINT,
                                product_id BIGINT,
                                address VARCHAR(255),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
