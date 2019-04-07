ALTER TABLE bolsos.mg_device_suggestions ADD COLUMN sell_count integer;
ALTER TABLE bolsos.mg_device_suggestions ADD COLUMN add_count integer;
ALTER TABLE bolsos.mg_device_suggestions ADD COLUMN remove_count integer;
ALTER TABLE bolsos.mg_device_suggestions ADD COLUMN share_count integer;

ALTER TABLE bolsos.mg_device_suggestions RENAME COLUMN count TO count_suggested;
ALTER TABLE bolsos.mg_device_suggestions RENAME COLUMN count_right TO count;

CREATE SEQUENCE bolsos.seq_product_data_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER SEQUENCE bolsos.seq_product_data_id OWNED BY bolsos.mg_product_data.id;


