CREATE TABLE bolsos.mg_config(
    id integer NOT NULL,
    code character varying (20) NOT NULL,
    value character varying (50),
    description character varying (150),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_config ADD CONSTRAINT mg_config_id_pk PRIMARY KEY (id);

CREATE SEQUENCE bolsos.seq_config_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_config_id OWNED BY bolsos.mg_config.id;

insert into bolsos.mg_config
values(nextval('bolsos.seq_config_id'),'START_MINING',null,'date/time whe the process dataminig job starts', current_timestamp);

insert into bolsos.mg_jobs
values(nextval('bolsos.seq_jobs_id'),'GenerateDataJob','atelier1','0 0 12 * * ?',null,null,current_timestamp, current_timestamp);

CREATE TABLE bolsos.mg_product_data(
    id integer NOT NULL,
    product_id integer NOT NULL,
    cluster character varying (20) NOT NULL,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_product_data ADD CONSTRAINT mg_product_data_id_pk FOREIGN KEY (id) REFERENCES bolsos.mg_product (id) MATCH FULL;
ALTER TABLE bolsos.mg_product_data ADD CONSTRAINT mg_product_data_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;

CREATE TABLE bolsos.mg_device_suggestions(
    id integer NOT NULL,
    device_id integer NOT NULL,
    code_suggestion character varying (50) NOT NULL,
    type_suggestion character varying (20) NOT NULL,
    count integer,
    count_right integer,
    last_modification timestamp without time zone DEFAULT now(),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_device_suggestions ADD CONSTRAINT mg_device_suggestions_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_device_suggestions ADD CONSTRAINT mg_device_suggestions_device_id_pk FOREIGN KEY (device_id) REFERENCES bolsos.mg_device (id) MATCH FULL;


CREATE SEQUENCE bolsos.seq_device_suggestions_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_device_suggestions_id OWNED BY bolsos.mg_device_suggestions.id;


