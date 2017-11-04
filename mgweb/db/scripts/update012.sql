CREATE TABLE mg_product_type (
    id integer NOT NULL,
    type_code character varying (15) NOT NULL,
    name_trans_id integer,
    description_trans_id integer,
    url_trans_id integer,
    image_id integer,
    image_head_id integer,
    tag character varying (25),
    status_code character varying (10) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_id_pk PRIMARY KEY (id);
ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_image_id_pk FOREIGN KEY (image_id) REFERENCES mg_image (id) MATCH FULL;
ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_image_id_pk FOREIGN KEY (image_head_id) REFERENCES mg_image (id) MATCH FULL;
ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES mg_translation (id) MATCH FULL;
ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES mg_translation (id) MATCH FULL;
ALTER TABLE mg_product_type ADD CONSTRAINT mg_product_type_translation_url_id_pk FOREIGN KEY (url_trans_id) REFERENCES mg_translation (id) MATCH FULL;

CREATE SEQUENCE seq_product_type_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_product_type_id OWNED BY mg_product_type.id;
