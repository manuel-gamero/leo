
ALTER TABLE bolsos.mg_custom_component_collection ADD COLUMN name_trans_id integer;
ALTER TABLE bolsos.mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;

ALTER TABLE bolsos.mg_custom_component_collection ADD COLUMN description_trans_id integer;
ALTER TABLE bolsos.mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;


ALTER TABLE bolsos.mg_item ADD COLUMN name_image character varying(25);

ALTER TABLE bolsos.mg_users ADD COLUMN first_name character varying(50) NOT NULL;
ALTER TABLE bolsos.mg_users ADD COLUMN last_name character varying(50) NOT NULL;
ALTER TABLE bolsos.mg_users ADD COLUMN telephone character varying(25);

ALTER TABLE bolsos.mg_method_shipping DROP COLUMN description RESTRICT;
ALTER TABLE bolsos.mg_method_shipping ADD COLUMN name_trans_id integer;
ALTER TABLE bolsos.mg_method_shipping ADD COLUMN description_trans_id integer;
ALTER TABLE bolsos.mg_method_shipping ADD CONSTRAINT mg_method_shipping_name_trans_id_pk FOREIGN KEY (name_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;
ALTER TABLE bolsos.mg_method_shipping ADD CONSTRAINT mg_method_shipping_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;


ALTER TABLE bolsos.mg_user_address ADD COLUMN first_name character varying(50);
ALTER TABLE bolsos.mg_user_address ADD COLUMN last_name character varying(50);

--ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN billing_address_id integer;
--ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_billing_address_id_pk FOREIGN KEY (billing_address_id) REFERENCES bolsos.mg_user_address (id) MATCH FULL;
ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_shipping_address_id_pk FOREIGN KEY (shipping_address_id) REFERENCES bolsos.mg_user_address (id) MATCH FULL;

ALTER TABLE bolsos.mg_method_shipping ADD COLUMN country character varying(225) NOT NULL DEFAULT 'CA'

ALTER TABLE bolsos.mg_product DROP COLUMN msrp RESTRICT;
ALTER TABLE bolsos.mg_product ADD COLUMN msrp integer;
ALTER TABLE bolsos.mg_product ADD CONSTRAINT mg_product_price_id_pk FOREIGN KEY (msrp) REFERENCES bolsos.mg_price (id) MATCH FULL;


ALTER TABLE bolsos.mg_method_shipping DROP COLUMN price RESTRICT;
ALTER TABLE bolsos.mg_method_shipping ADD COLUMN price integer;
ALTER TABLE bolsos.mg_method_shipping ADD CONSTRAINT mg_method_shipping_price_id_pk FOREIGN KEY (price) REFERENCES bolsos.mg_price (id) MATCH FULL;

ALTER TABLE bolsos.mg_price_entry ADD CONSTRAINT CONST_UNIQUE_PRICE_ENTRY UNIQUE (price_id,currency);


ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN currency character varying(3) NOT NULL;
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN reference character varying(20) NOT NULL;
ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_transaction_id_pk FOREIGN KEY (transaction_id) REFERENCES bolsos.mg_transaction (id) MATCH SIMPLE;



ALTER TABLE bolsos.mg_collection ADD COLUMN image_id integer;
ALTER TABLE bolsos.mg_collection ADD CONSTRAINT mg_collection_image_id_pk FOREIGN KEY (image_id) REFERENCES bolsos.mg_image (id) MATCH SIMPLE;


ALTER TABLE bolsos.mg_custom_component_text ADD COLUMN image_width integer;
ALTER TABLE bolsos.mg_custom_component_text ADD COLUMN image_height integer;

