ALTER TABLE bolsos.mg_product ADD COLUMN custom_link_id integer;

CREATE TABLE bolsos.mg_product_order(
    id integer NOT NULL,
    product_id integer NOT NULL,
    product_order integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_product_order ADD CONSTRAINT mg_product_order_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_product_order ADD CONSTRAINT mg_product_order_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_product_order_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_product_order_id OWNED BY bolsos.mg_product_order.id;
