--mg_audit_hist
CREATE TABLE bolsos.mg_audit_hist (
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

ALTER TABLE bolsos.mg_audit_hist ADD CONSTRAINT mg_audit_hist_id_pk PRIMARY KEY (id);

CREATE SEQUENCE bolsos.seq_audit_hist_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_audit_hist_id OWNED BY bolsos.mg_audit_hist.id;

-- device
CREATE TABLE bolsos.mg_device (
    id integer NOT NULL,
    fingerprint character varying (50) NOT NULL,
    user_agent character varying (200) NOT NULL,
    language character varying (5) NOT NULL,
    color_depth character varying (5),
    pixel_ratio integer,
    movil boolean,
    resolution character varying (10),
    available_resolution character varying (10),
    timezone_offset character varying (5),
    cpu_class character varying (20),
    navigator_platform character varying (20),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device ADD CONSTRAINT mg_device_id_pk PRIMARY KEY (id);

CREATE SEQUENCE bolsos.seq_device_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_id OWNED BY bolsos.mg_device.id;

insert into bolsos.mg_device (id, fingerprint, user_agent, language)
values
(nextval('bolsos.seq_device_id'), 'SYSTEM', 'SYSTEM', 'ES-ES');

insert into bolsos.mg_device (id, fingerprint, user_agent, language)
values
(nextval('bolsos.seq_device_id'), 'UNKNOW', 'UNKNOW', 'ES-ES');


--Device user
CREATE TABLE bolsos.mg_device_user (
    id integer NOT NULL,
    device_id integer NOT NULL,
    user_id integer NOT NULL,
    count integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_user ADD CONSTRAINT mg_device_user_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_user ADD CONSTRAINT mg_device_user_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_user ADD CONSTRAINT mg_device_user_user_id_pk FOREIGN KEY (user_id) REFERENCES bolsos.mg_users (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_user_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_user_id OWNED BY bolsos.mg_device_user.id;


-- Device product

CREATE TABLE bolsos.mg_device_product (
    id integer NOT NULL,
    device_id integer NOT NULL,
    product_id integer NOT NULL,
    count integer,
    share_count integer,
    add_count integer,
    sell_count integer,
    remove_count integer,
    last_modification timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_product ADD CONSTRAINT mg_device_product_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_product ADD CONSTRAINT mg_device_product_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_product ADD CONSTRAINT mg_device_product_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_product_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_product_id OWNED BY bolsos.mg_device_product.id;

-- Device collection

CREATE TABLE bolsos.mg_device_collection (
    id integer NOT NULL,
    device_id integer NOT NULL,
    collection_id integer NOT NULL,
    count integer,
    last_modification timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_collection ADD CONSTRAINT mg_device_collection_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_collection ADD CONSTRAINT mg_device_collection_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_collection ADD CONSTRAINT mg_device_collection_collection_id_pk FOREIGN KEY (collection_id) REFERENCES bolsos.mg_collection (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_collection_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_collection_id OWNED BY bolsos.mg_device_collection.id;

-- Device component

CREATE TABLE bolsos.mg_device_component (
    id integer NOT NULL,
    device_id integer NOT NULL,
    component_id integer NOT NULL,
    count integer,
    share_count integer,
    add_count integer,
    sell_count integer,
    remove_count integer,
    last_modification timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_component ADD CONSTRAINT mg_device_component_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_component ADD CONSTRAINT mg_device_component_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_component ADD CONSTRAINT mg_device_component_component_id_pk FOREIGN KEY (component_id) REFERENCES bolsos.mg_custom_component_collection (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_component_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_component_id OWNED BY bolsos.mg_device_component.id;

-- Device product historical

CREATE TABLE bolsos.mg_device_product_hist (
    id integer NOT NULL,
    device_id integer NOT NULL,
    product_id integer NOT NULL,
    duration bigint,
    user_action character varying(10),
    action_date timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_product_hist ADD CONSTRAINT mg_device_product_hist_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_product_hist ADD CONSTRAINT mg_device_product_hist_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_product_hist ADD CONSTRAINT mg_device_product_hist_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_product_hist_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_product_hist_id OWNED BY bolsos.mg_device_product_hist.id;

-- Device collection historical

CREATE TABLE bolsos.mg_device_collection_hist (
    id integer NOT NULL,
    device_id integer NOT NULL,
    collection_id integer NOT NULL,
    duration integer,
    action_date timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_collection_hist ADD CONSTRAINT mg_device_collection_hist_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_collection_hist ADD CONSTRAINT mg_device_collection_hist_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_collection_hist ADD CONSTRAINT mg_device_collection_hist_collection_id_pk FOREIGN KEY (collection_id) REFERENCES bolsos.mg_collection (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_collection_hist_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_collection_hist_id OWNED BY bolsos.mg_device_collection_hist.id;

-- Device component historical

CREATE TABLE bolsos.mg_device_component_hist (
    id integer NOT NULL,
    device_id integer NOT NULL,
    component_id integer NOT NULL,
    user_action character varying(10),
    action_date timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_component_hist ADD CONSTRAINT mg_device_component_hist_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_component_hist ADD CONSTRAINT mg_device_component_hist_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;
ALTER TABLE bolsos.mg_device_component_hist ADD CONSTRAINT mg_device_component_hist_component_id_pk FOREIGN KEY (component_id) REFERENCES bolsos.mg_custom_component_collection (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_component_hist_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_component_hist_id OWNED BY bolsos.mg_device_component_hist.id;


--User action parameter
CREATE TABLE bolsos.mg_device_user_action_param (
    id integer NOT NULL,
    device_hist_id integer NOT NULL,
    param character varying(20) NOT NULL,
    param_value character varying(20) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_user_action_param ADD CONSTRAINT mg_device_user_action_param_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_user_action_param ADD CONSTRAINT mg_device_user_action_param_device_product_hist_id_pk FOREIGN KEY (device_hist_id) REFERENCES bolsos.mg_device_product_hist(id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_user_action_param_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_user_action_param_id OWNED BY bolsos.mg_device_user_action_param.id;


-- Device url

CREATE TABLE bolsos.mg_device_url (
    id integer NOT NULL,
    device_id integer NOT NULL,
    url character varying(200),
    ajax boolean DEFAULT false,
    duration bigint,
    action_date timestamp,
    url_from character varying(200),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_url ADD CONSTRAINT mg_device_url_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_url ADD CONSTRAINT mg_device_url_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_url_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_url_id OWNED BY bolsos.mg_device_url.id;


-- Device session

CREATE TABLE bolsos.mg_device_session (
    id integer NOT NULL,
    device_id integer NOT NULL,
    duration bigint,
    action_date timestamp,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_session ADD CONSTRAINT mg_device_session_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_session ADD CONSTRAINT mg_device_session_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_device_session_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_session_id OWNED BY bolsos.mg_device_session.id;

-- Configuration


--Change size image name in Item table
alter table bolsos.mg_item alter column name_image type character varying(50);