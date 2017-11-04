
ALTER TABLE bolsos.mg_method_shipping ADD COLUMN status_code character varying (10);

ALTER TABLE bolsos.mg_user_address ADD COLUMN city character varying (30);

ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN extras character varying (10);

--Add lang column where set up language user when the user is created
ALTER TABLE bolsos.mg_users ADD COLUMN lang character varying (3)DEFAULT 'fr';
UPDATE bolsos.mg_users set lang = 'fr';
ALTER TABLE bolsos.mg_users ALTER  COLUMN lang SET NOT NULL;


--Add color column to item
ALTER TABLE bolsos.mg_item ADD COLUMN color character varying (10);


--Add shopping cart total shopping
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN total_shopping numeric;

update mg_shopping_cart
set total_shopping = CAST(total AS numeric) + CAST(shipping_fees AS numeric) + CAST(taxes AS numeric) + CAST(coalesce(extras,'0.00') AS numeric)


