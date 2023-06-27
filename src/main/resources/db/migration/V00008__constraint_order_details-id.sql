ALTER TABLE order_details
    ADD CONSTRAINT order_details_payment_id_unique UNIQUE (payment_id);