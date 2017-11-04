CREATE TABLE bolsos.mg_jobs(
    id integer NOT NULL,
    name character varying (25) NOT NULL,
    group_job character varying (10),
    schedule character varying (25),
    value1 character varying (25),
    value2 character varying (25),
    update_date timestamp without time zone DEFAULT now(),
    creation_date timestamp without time zone DEFAULT now()
);

ALTER TABLE bolsos.mg_jobs ADD CONSTRAINT mg_jobs_id_pk PRIMARY KEY (id);

CREATE SEQUENCE bolsos.seq_jobs_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
    
ALTER SEQUENCE bolsos.seq_jobs_id OWNED BY bolsos.mg_jobs.id;


insert into bolsos.mg_jobs
values(nextval('bolsos.seq_jobs_id'),'ResetFeedJob','atelier1','0 0 12 * * ?',null,null,current_timestamp, current_timestamp);


