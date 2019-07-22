CREATE TABLE bolsos.mg_suggestions(
    id integer NOT NULL,
    product_id integer NOT NULL,
    suggestion character varying (50),
    session_guid character varying(32) NOT NULL,
    user_id integer,   
    count integer,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_suggestions ADD CONSTRAINT mg_suggestions_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_suggestions ADD CONSTRAINT mg_suggestions_product_id_pk FOREIGN KEY (product_id) REFERENCES bolsos.mg_product (id) MATCH FULL;
ALTER TABLE bolsos.mg_suggestions ADD CONSTRAINT mg_suggestions_user_id_pk FOREIGN KEY (user_id) REFERENCES bolsos.mg_users (id) MATCH FULL;

CREATE SEQUENCE bolsos.seq_suggestions_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_suggestions_id OWNED BY bolsos.mg_suggestions.id;

CREATE INDEX mg_suggestions_suggestion_session_guid_idx ON bolsos.mg_suggestions (suggestion,session_guid);
CREATE INDEX mg_suggestions_session_guid_idx ON bolsos.mg_suggestions (session_guid);

insert into bolsos.mg_jobs
values(nextval('bolsos.seq_jobs_id'),'GenerateMinigDataJob','atelier1','0 0 12 * * ?','16',null,current_timestamp, current_timestamp);

--Change size server_name in mg_audit_hist table
alter table bolsos.mg_audit_hist alter column server_name type character varying(40);

ALTER TABLE bolsos.mg_product_data DROP CONSTRAINT mg_product_data_id_pk;
ALTER TABLE bolsos.mg_product_data ADD CONSTRAINT mg_product_data_id_pk PRIMARY KEY (id);