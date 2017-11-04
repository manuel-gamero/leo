
--mg_product_image
CREATE TABLE bolsos.mg_product_image (
    id integer NOT NULL,
    product_id integer NOT NULL,
    image_id integer NOT NULL,
    name_trans_id integer,
    description_trans_id integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_product_image ADD CONSTRAINT mg_product_image_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_product_image ADD CONSTRAINT mg_product_image_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;
ALTER TABLE bolsos.mg_product_image ADD CONSTRAINT mg_product_image_image_id_pk FOREIGN KEY (image_id) REFERENCES bolsos.mg_image(id) MATCH FULL;
ALTER TABLE bolsos.mg_product_image ADD CONSTRAINT mg_product_image_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;
ALTER TABLE bolsos.mg_product_image ADD CONSTRAINT mg_product_image_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_product_image_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_product_image_id OWNED BY bolsos.mg_product_image.id;