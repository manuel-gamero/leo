--Translation
CREATE TABLE mg_translation (
    id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_translation ADD CONSTRAINT mg_translation_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_translation_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_translation_id OWNED BY mg_translation.id;


CREATE TABLE mg_translation_entry (
    id integer NOT NULL,
    trans_id integer NOT NULL,
    lang_code character varying (3) NOT NULL,
    text character varying NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_translation_entry ADD CONSTRAINT mg_translation_entry_id_pk PRIMARY KEY (id);
ALTER TABLE mg_translation_entry ADD CONSTRAINT mg_translation_entry_translation_id_pk FOREIGN KEY (trans_id) REFERENCES mg_translation (id) MATCH FULL;


CREATE SEQUENCE seq_translation_entry_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_translation_entry_id OWNED BY mg_translation_entry.id;



--User
CREATE TABLE mg_users (
    id integer NOT NULL,
    login character varying(50) NOT NULL,
    type_code character varying (10) NOT NULL,
    status_code character varying (10) NOT NULL,
    password character varying(500) NOT NULL,
    email character varying(320) NOT NULL,
    last_login_date timestamp without time zone,
    active boolean DEFAULT true,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_users ADD CONSTRAINT mg_users_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_user_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_user_id OWNED BY mg_users.id;

insert into mg_users
(id, login, type_code, status_code, password, email)
values
(nextval('seq_user_id'), 'admin', 'ADMIN', 'ACTIVE', 'admin', 'admin@bolsos.es' );

CREATE TABLE mg_user_address (
    id integer NOT NULL,
    user_id integer NOT NULL,
    type_code character varying (10) NOT NULL,
    street character varying(225) NOT NULL,
    apartment character varying(225),
    post_code character varying(100) NOT NULL,
    province character varying(225) NOT NULL,
    country character varying(225) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_user_address ADD CONSTRAINT mg_user_address_id_pk PRIMARY KEY (id);
ALTER TABLE mg_user_address ADD CONSTRAINT mg_user_address_user_id_pk FOREIGN KEY (user_id) REFERENCES mg_users (id) MATCH FULL;


CREATE SEQUENCE seq_user_address_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_user_address_id OWNED BY mg_user_address.id;


CREATE TABLE mg_image (
    id integer NOT NULL,
    name character varying(120) NOT NULL,
    type_code character varying (10) NOT NULL,
    size integer,
    height integer,
    width integer,
    large boolean DEFAULT false NOT NULL,
    upload_date timestamp without time zone DEFAULT now() NOT NULL,
    resolution integer DEFAULT 96 NOT NULL,
    score integer NOT NULL,
    real_name character varying(120) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_image ADD CONSTRAINT mg_image_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_image_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_image_id OWNED BY mg_image.id;


--Product

CREATE TABLE mg_collection
(
	id integer NOT NULL,
	code character varying (10) NOT NULL,
	name_trans_id integer,
	description_trans_id integer,
	name character varying(50) NOT NULL,
    description character varying(225) NOT NULL,
    status_code character varying (10) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_collection ADD CONSTRAINT mg_collection_id_pk PRIMARY KEY (id);
ALTER TABLE mg_collection ADD CONSTRAINT mg_collection_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES mg_translation (id) MATCH FULL;
ALTER TABLE mg_collection ADD CONSTRAINT mg_collection_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES mg_translation (id) MATCH FULL;


CREATE SEQUENCE seq_collection_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_collection_id OWNED BY mg_collection.id;

CREATE TABLE mg_custom_component (
    id integer NOT NULL,
    type_code character varying (10) NOT NULL,
	code character varying (10) NOT NULL,
    name character varying(120) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_custom_component ADD CONSTRAINT mg_custom_component_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_custom_component_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_custom_component_id OWNED BY mg_custom_component.id;
--Tabla que guarda la logica que es lo que se puede seleccionar para una determinada
--coleccion y un determinado componente
CREATE TABLE mg_custom_component_collection (
    id integer NOT NULL,
    custom_id integer NOT NULL,
    collection_id integer,
    type_code character varying (10) NOT NULL,
    status_code character varying (10) NOT NULL,
    value character varying(120),
    image_id integer,
    description_trans_id integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_id_pk PRIMARY KEY (id);
ALTER TABLE mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_custom_component_id_pk FOREIGN KEY (custom_id) REFERENCES mg_custom_component (id) MATCH FULL;
ALTER TABLE mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_collection_id_pk FOREIGN KEY (collection_id) REFERENCES mg_collection (id) MATCH FULL;
ALTER TABLE mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_image_id_pk FOREIGN KEY (image_id) REFERENCES mg_image (id) MATCH SIMPLE;
ALTER TABLE mg_custom_component_collection ADD CONSTRAINT mg_custom_component_collection_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES mg_translation (id) MATCH FULL;


CREATE SEQUENCE seq_custom_component_collection_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_custom_component_collection_id OWNED BY mg_custom_component_collection.id;


CREATE TABLE mg_product (
    id integer NOT NULL,
    name_trans_id integer,
    description_trans_id integer,
    image_id integer,
    collection_id integer,
    status_code character varying (10) NOT NULL,
    msrp character varying(15),
    cost character varying(15),
    type_code character varying (15) NOT NULL,
    weight character varying(10),
    width character varying(10),
    height character varying(10),
    depth character varying(10),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_product ADD CONSTRAINT mg_product_id_pk PRIMARY KEY (id);
ALTER TABLE mg_product ADD CONSTRAINT mg_product_collection_id_pk FOREIGN KEY (collection_id) REFERENCES mg_collection (id) MATCH FULL;
ALTER TABLE mg_product ADD CONSTRAINT mg_product_image_id_pk FOREIGN KEY (image_id) REFERENCES mg_image (id) MATCH FULL;
ALTER TABLE mg_product ADD CONSTRAINT mg_product_translation_name_id_pk FOREIGN KEY (name_trans_id) REFERENCES mg_translation (id) MATCH FULL;
ALTER TABLE mg_product ADD CONSTRAINT mg_product_translation_description_id_pk FOREIGN KEY (description_trans_id) REFERENCES mg_translation (id) MATCH FULL;

CREATE SEQUENCE seq_product_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_product_id OWNED BY mg_product.id;

CREATE TABLE mg_product_tag (
    id integer NOT NULL,
    product_id integer NOT NULL,
    tag character varying(255),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_product_tag ADD CONSTRAINT mg_product_tag_id_pk PRIMARY KEY (id);
ALTER TABLE mg_product_tag ADD CONSTRAINT mg_product_tag_product_id_pk FOREIGN KEY (product_id) REFERENCES mg_product (id) MATCH FULL;

CREATE SEQUENCE seq_product_tag_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_product_tag_id OWNED BY mg_product_tag.id;


-- Tabla que guarda la relacion entre la imagen del producto y las mascaras
CREATE TABLE mg_custom_component_image (
    id integer NOT NULL,
    component_collection_id integer NOT NULL,
    image_id integer NOT NULL,
    image_mask_id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_custom_component_image ADD CONSTRAINT mg_custom_component_image_id_pk PRIMARY KEY (id);
ALTER TABLE mg_custom_component_image ADD CONSTRAINT mg_custom_component_image_component_collection_id_pk FOREIGN KEY (component_collection_id) REFERENCES mg_custom_component_collection (id) MATCH FULL;
ALTER TABLE mg_custom_component_image ADD CONSTRAINT mg_custom_component_image_image_id_pk FOREIGN KEY (image_id) REFERENCES mg_image (id) MATCH FULL;
ALTER TABLE mg_custom_component_image ADD CONSTRAINT mg_custom_component_image_image_mask_id_pk FOREIGN KEY (image_mask_id) REFERENCES mg_image (id) MATCH FULL;

CREATE SEQUENCE seq_custom_component_image_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_custom_component_image_id OWNED BY mg_custom_component_image.id;

--Tabla que guarda la relacion entre la mascara y el texto que se puede poner en ella
CREATE TABLE mg_custom_component_text (
    id integer NOT NULL,
    image_id integer NOT NULL,
    pos_top integer NOT NULL,
    pos_left integer NOT NULL,
    width integer NOT NULL,
    height integer NOT NULL,
    align character varying (7) NOT NULL,
    maximum integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_custom_component_text ADD CONSTRAINT mg_custom_component_text_id_pk PRIMARY KEY (id);
ALTER TABLE mg_custom_component_text ADD CONSTRAINT mg_custom_component_text_image_id_pk FOREIGN KEY (image_id) REFERENCES mg_image (id) MATCH FULL;

CREATE SEQUENCE seq_custom_component_text_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_custom_component_text_id OWNED BY mg_custom_component_text.id;

--Tablas que guarda la informacion de de un item. Se entiende por item, la relacion entre cada
--cada CustomerComponentImage que forma parte del producto con su correspondiente CustomerCompoentCollection  
--y el texto que la persona ha escrito consu tipo de fuente y tama�o
CREATE TABLE mg_item (
    id integer NOT NULL,
    product_id integer NOT NULL,
    text character varying(50),
    font character varying(120),
    size character varying(120),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_item ADD CONSTRAINT mg_item_id_pk PRIMARY KEY (id);
ALTER TABLE mg_item ADD CONSTRAINT mg_item_product_id_pk FOREIGN KEY (product_id) REFERENCES mg_product (id) MATCH FULL;

CREATE SEQUENCE seq_item_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_item_id OWNED BY mg_item.id;

CREATE TABLE mg_item_component (
    id integer NOT NULL,
    item_id integer NOT NULL,
    component_image_id integer NOT NULL,
    component_collection_id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_item_component ADD CONSTRAINT mg_item_component_id_pk PRIMARY KEY (id);
ALTER TABLE mg_item_component ADD CONSTRAINT mg_item_component_item_id_pk FOREIGN KEY (item_id) REFERENCES mg_item (id) MATCH FULL;
ALTER TABLE mg_item_component ADD CONSTRAINT mg_item_component_component_image_id_pk FOREIGN KEY (component_image_id) REFERENCES mg_custom_component_image (id) MATCH FULL;
ALTER TABLE mg_item_component ADD CONSTRAINT mg_item_component_component_collection_id_pk FOREIGN KEY (component_collection_id) REFERENCES mg_custom_component_collection (id) MATCH FULL;

CREATE SEQUENCE seq_item_component_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_item_component_id OWNED BY mg_item_component.id;


CREATE TABLE mg_taxes
(
	id integer NOT NULL,
	code character varying (10) NOT NULL,
    description character varying(225) NOT NULL,
    tax character varying(15) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_taxes ADD CONSTRAINT mg_taxes_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_taxes_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_taxes_id OWNED BY mg_taxes.id;

CREATE TABLE mg_method_shipping
(
	id integer NOT NULL,
	code character varying (10) NOT NULL,
    description character varying(225) NOT NULL,
    price character varying(15) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_method_shipping ADD CONSTRAINT mg_method_shipping_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_method_shipping_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_method_shipping_id OWNED BY mg_method_shipping.id;

--Tabla que guarda la informacion de la compra
CREATE TABLE mg_shopping_cart (
    id integer NOT NULL,
    user_id integer NOT NULL,
    transaction_id integer,
    status_code character varying (10) NOT NULL,
    method_shipping_id integer NOT NULL,
    total character varying(15) NOT NULL,
    shipping_fees character varying (10),
    taxes character varying (10)NOT NULL,
    shipping_address_id integer NOT NULL,
    track_number character varying(80),
    payment_method character varying (20)NOT NULL,
    purchase_date timestamp,
    send_date timestamp,
    shipping_date timestamp,
    comment character varying(255),
    comment_user character varying(255),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_id_pk PRIMARY KEY (id);
ALTER TABLE mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_user_id_pk FOREIGN KEY (user_id) REFERENCES mg_users (id) MATCH FULL;
ALTER TABLE mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_component_method_shipping_id_pk FOREIGN KEY (method_shipping_id) REFERENCES mg_method_shipping (id) MATCH FULL;
ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT CONST_UNIQUE_REFERENCE UNIQUE (reference);

CREATE SEQUENCE seq_shopping_cart_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_shopping_cart_id OWNED BY mg_shopping_cart.id;


CREATE TABLE mg_shopping_cart_item (
    id integer NOT NULL,
    shopping_cart_id integer NOT NULL,
    item_id integer NOT NULL,
    status_code character varying (10) NOT NULL,
    price character varying(15) NOT NULL,
    quantity integer,
    discount character varying(15),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_shopping_cart_item ADD CONSTRAINT mg_shopping_cart_item_id_pk PRIMARY KEY (id);
ALTER TABLE mg_shopping_cart_item ADD CONSTRAINT mg_shopping_cart_item_shopping_cart_id_pk FOREIGN KEY (shopping_cart_id) REFERENCES mg_shopping_cart (id) MATCH FULL;
ALTER TABLE mg_shopping_cart_item ADD CONSTRAINT mg_shopping_cart_item_item_id_pk FOREIGN KEY (item_id) REFERENCES mg_item (id) MATCH FULL;

CREATE SEQUENCE seq_shopping_cart_item_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_shopping_cart_item_id OWNED BY mg_shopping_cart_item.id;

CREATE TABLE mg_logs (
    id integer NOT NULL,
    log_time timestamp without time zone DEFAULT now(),
    category character varying (100),
    severity character varying (5),
    message character varying (2000),
    server_name character varying (5)    
);

ALTER TABLE mg_logs ADD CONSTRAINT mg_logs_id_pk PRIMARY KEY (id);


CREATE SEQUENCE seq_logs_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;    

ALTER SEQUENCE seq_logs_id OWNED BY mg_logs.id;

CREATE TABLE mg_audit (
	id integer NOT NULL,
	username character varying(50),
	location character varying(64),
	session_guid character varying(32),
	request_guid character varying(32),
	creation_date timestamp without time zone DEFAULT now(),
	action_user character varying(128),
	outcome character varying(128),
	message character varying(2000),
	server_name character varying(15),
	browser character varying(255),
	url character varying(2000),
	call_duration integer);

ALTER TABLE mg_audit ADD CONSTRAINT mg_audit_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_audit_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE seq_audit_id OWNED BY mg_audit.id;

--Table group for currency logic
CREATE TABLE mg_price (
    id integer NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_price ADD CONSTRAINT mg_price_id_pk PRIMARY KEY (id);

CREATE SEQUENCE seq_price_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_price_id OWNED BY mg_price.id;


CREATE TABLE mg_price_entry (
    id integer NOT NULL,
    price_id integer NOT NULL,
    price character varying(15) NOT NULL,
    currency character varying (5) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_price_entry ADD CONSTRAINT mg_price_entry_id_pk PRIMARY KEY (id);
ALTER TABLE mg_price_entry ADD CONSTRAINT mg_price_entry_translation_id_pk FOREIGN KEY (price_id) REFERENCES mg_price (id) MATCH FULL;


CREATE SEQUENCE seq_price_entry_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_price_entry_id OWNED BY mg_price_entry.id;

CREATE TABLE mg_transaction (
    id integer NOT NULL,
    TPE character varying(15) NOT NULL,
    purchase_date timestamp without time zone,
    MAC character varying(100) NOT NULL,
    montant character varying(15) NOT NULL,
    reference character varying(20) NOT NULL,
    texte_libre character varying(255),
    code_retour character varying(10) NOT NULL,
    cvx character varying(20),
    vld character varying(10),
    brand character varying(20),
    status3ds character varying(3),
    originecb character varying(10),
    bincb character varying(20),
    hpancb character varying(100),
    ipclient character varying(20),
    originetr character varying(10),
    filtragecause character varying(2),
    cbenregistree character varying(1),
    cbmasquee character varying(16),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE mg_transaction ADD CONSTRAINT mg_transaction_id_pk PRIMARY KEY (id);
ALTER TABLE mg_transaction ADD CONSTRAINT mg_transaction_reference_pk FOREIGN KEY (reference) REFERENCES mg_shopping_cart (reference) MATCH FULL;


CREATE SEQUENCE seq_transaction_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE seq_transaction_id OWNED BY mg_transaction.id;

