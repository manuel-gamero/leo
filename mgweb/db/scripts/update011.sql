alter table bolsos.mg_audit alter column server_name type character varying(40);

update bolsos.mg_device 
set fingerprint = 'UNKNOWN', user_agent ='UNKNOWN'
where fingerprint = 'UNKNOW'


--Paypal tables
CREATE TABLE bolsos.mg_paypal (
    id integer NOT NULL,
    token character varying(50) NOT NULL,
    reference character varying(20) NOT NULL,
    payerid character varying(15),
    correlationid character varying(25),
    errorcode character varying(10),
    ack character varying(15),
    severitycode character varying(15),
    shormessage character varying(150),
    longmessage character varying(255),
    update_date timestamp without time zone,
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_paypal ADD CONSTRAINT mg_paypal_id_pk PRIMARY KEY (id);
ALTER TABLE bolsos.mg_paypal ADD CONSTRAINT mg_paypal_reference_pk FOREIGN KEY (reference) REFERENCES bolsos.mg_shopping_cart (reference) MATCH FULL;


CREATE SEQUENCE bolsos.seq_paypal_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_paypal_id OWNED BY bolsos.mg_paypal.id;

CREATE INDEX paypal_token_idx ON bolsos.mg_paypal (token);


ALTER TABLE bolsos.mg_shopping_cart ADD COLUMN paypal_id integer;

ALTER TABLE bolsos.mg_shopping_cart ADD CONSTRAINT mg_shopping_cart_paypal_id_pk FOREIGN KEY (paypal_id) REFERENCES bolsos.mg_paypal (id) MATCH SIMPLE;

alter table bolsos.mg_transaction alter column code_retour type character varying(20);

alter table bolsos.mg_device alter column user_agent type character varying(255);


