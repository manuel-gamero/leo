ALTER TABLE bolsos.mg_coupons_type ADD COLUMN promotion boolean NOT NULL DEFAULT false;
ALTER TABLE bolsos.mg_coupons_type ADD COLUMN promotion_start timestamp without time zone DEFAULT null;
ALTER TABLE bolsos.mg_coupons_type ADD COLUMN promotion_end timestamp without time zone DEFAULT null;

ALTER TABLE bolsos.mg_coupons_type ADD COLUMN image_en_id integer;
ALTER TABLE bolsos.mg_coupons_type ADD COLUMN image_fr_id integer;
ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_image_en_id_pk FOREIGN KEY (image_en_id) REFERENCES bolsos.mg_image (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_image_fr_id_pk FOREIGN KEY (image_fr_id) REFERENCES bolsos.mg_image (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE bolsos.mg_coupons_type ADD COLUMN url_trans_id integer;
ALTER TABLE bolsos.mg_coupons_type ADD CONSTRAINT mg_coupons_type_translation_url_id_pk FOREIGN KEY (url_trans_id) REFERENCES bolsos.mg_translation (id) MATCH FULL;

