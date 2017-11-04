


ALTER TABLE bolsos.mg_shopping_cart_item DROP CONSTRAINT mg_shopping_cart_item_item_id_pk;
ALTER TABLE bolsos.mg_shopping_cart_item DROP CONSTRAINT mg_shopping_cart_item_shopping_cart_id_pk;

delete from bolsos.mg_item where id in (select item_id
from bolsos.mg_shopping_cart_item
where shopping_cart_id < 220);


delete from bolsos.mg_shopping_cart_item
where shopping_cart_id < 220;

ALTER TABLE bolsos.mg_transaction DROP CONSTRAINT mg_transaction_reference_pk;
ALTER TABLE bolsos.mg_shopping_cart DROP CONSTRAINT mg_shopping_cart_transaction_id_pk;

delete from bolsos.mg_transaction where reference in (select reference
						from bolsos.mg_shopping_cart
						where id < 220);

delete from bolsos.mg_shopping_cart where id < 220;


ALTER TABLE bolsos.mg_shopping_cart_item ADD CONSTRAINT mg_shopping_cart_item_shopping_cart_id_pk FOREIGN KEY (shopping_cart_id) REFERENCES bolsos.mg_shopping_cart (id) MATCH FULL;
ALTER TABLE bolsos.mg_shopping_cart_item ADD CONSTRAINT mg_shopping_cart_item_item_id_pk FOREIGN KEY (item_id) REFERENCES bolsos.mg_item (id) MATCH FULL;
ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_transaction_id_pk FOREIGN KEY (transaction_id) REFERENCES bolsos.mg_transaction (id) MATCH SIMPLE;


delete from bolsos.mg_transaction where id <= 12;

ALTER TABLE bolsos.mg_transaction ADD CONSTRAINT mg_transaction_reference_pk FOREIGN KEY (reference) REFERENCES bolsos.mg_shopping_cart (reference) MATCH FULL;

