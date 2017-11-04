


--Coupons type
CREATE TABLE bolsos.mg_coupons_type (
    id integer NOT NULL,
    code character varying (30) NOT NULL,
    type_code character varying (30) NOT NULL,
    multi_time integer DEFAULT 1,
    multi_user integer DEFAULT 1,
    name_trans_id integer,
    description_trans_id integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;
ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES bolsos. mg_translation (id) MATCH FULL;


CREATE SEQUENCE bolsos.seq_coupons_type_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_coupons_type_id OWNED BY bolsos.mg_coupons_type.id;

--Coupons
CREATE TABLE bolsos.mg_coupons (
    id integer NOT NULL,
    coupon_type_id integer NOT NULL,
    status_code character varying (10) NOT NULL,
    coupon_name character varying(50) NOT NULL,
    inactive_date timestamp without time zone,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_coupons ADD CONSTRAINT mg_coupons_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_coupons ADD CONSTRAINT mg_coupons_coupons_type_id_pk FOREIGN KEY (coupon_type_id) REFERENCES bolsos.mg_coupons_type (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_coupons_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_coupons_id OWNED BY bolsos.mg_coupons.id;

--Coupons user
CREATE TABLE bolsos.mg_coupons_user (
    id integer NOT NULL,
    coupon_id integer NOT NULL,
    user_id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_coupons_user ADD CONSTRAINT mg_coupons_user_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_coupons_user ADD CONSTRAINT mg_coupons_user_coupon_id_pk FOREIGN KEY (coupon_id) REFERENCES bolsos.mg_coupons (id) MATCH FULL;
ALTER TABLE bolsos.mg_coupons_user ADD CONSTRAINT mg_coupons_user_user_id_pk FOREIGN KEY (user_id) REFERENCES bolsos.mg_users (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_coupons_user_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_coupons_user_id OWNED BY bolsos.mg_coupons_user.id;



--Discount
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN discount_total_shopping numeric(10,2);
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN discount_total numeric(10,2);
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN discount_shipping_fees numeric(10,2);
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN discount_taxes numeric(10,2);
ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN discount_extras numeric(10,2);

ALTER TABLE bolsos.mg_item ADD COLUMN discount_price numeric(10,2);


--Convertions
ALTER TABLE bolsos.mg_shopping_cart ALTER COLUMN total TYPE numeric(10,2) USING total::numeric;
ALTER TABLE bolsos.mg_shopping_cart ALTER COLUMN shipping_fees TYPE numeric(10,2) USING shipping_fees::numeric;
ALTER TABLE bolsos.mg_shopping_cart ALTER COLUMN taxes TYPE numeric(10,2) USING taxes::numeric;
ALTER TABLE bolsos.mg_shopping_cart ALTER COLUMN extras TYPE numeric(10,2) USING extras::numeric;
ALTER TABLE bolsos.mg_shopping_cart ALTER COLUMN total_shopping TYPE numeric(10,2) USING total_shopping::numeric;
ALTER TABLE bolsos.mg_shopping_cart_item ALTER COLUMN price TYPE numeric(10,2) USING price::numeric;

ALTER TABLE bolsos.mg_price_entry ALTER COLUMN price TYPE numeric(10,2) USING price::numeric;
ALTER TABLE bolsos.mg_taxes ALTER COLUMN tax TYPE numeric(10,2) USING tax::numeric;







