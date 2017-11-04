
ALTER TABLE bolsos.mg_product ADD COLUMN custom_product boolean NOT NULL DEFAULT true;
ALTER TABLE bolsos.mg_product ADD COLUMN new_product boolean NOT NULL DEFAULT false;
ALTER TABLE bolsos.mg_product ADD COLUMN custom_text boolean NOT NULL DEFAULT true;

ALTER TABLE bolsos.mg_price_entry ADD COLUMN discount numeric(10,2);


update bolsos.mg_image i
set real_name = (select '/images/product/' || p.id || '/' from bolsos.mg_product p, bolsos.mg_custom_component_image cci where cci.image_mask_id =  i.id and cci.image_id = p.image_id)
where i.real_name not like '%/%'
and i.type_code = 'MASK'
and (select '/images/product/' || p.id || '/' from bolsos.mg_product p, bolsos.mg_custom_component_image cci where cci.image_mask_id =  i.id and cci.image_id = p.image_id) is not null


update bolsos.mg_image i
set real_name = (select '/images/product/' || p.id || '/' from bolsos.mg_product p, bolsos.mg_product_image pi where pi.image_id =  i.id and p.id = pi.product_id)
where i.real_name not like '%/%'
and i.type_code = 'PRODGROUP'
and (select '/images/product/' || p.id || '/' from bolsos.mg_product p, bolsos.mg_product_image pi where pi.image_id =  i.id and p.id = pi.product_id) is not null


update bolsos.mg_image i
set real_name = (select case when p.id isnull then i.name else '/images/product/' || p.id || '/' end from bolsos.mg_product p where p.image_id =  i.id)
where i.real_name not like '%/%'
and i.type_code = 'PRODUCT'
and (select case when p.id isnull then i.name else '/images/product/' || p.id || '/' end from bolsos.mg_product p where p.image_id =  i.id) is not null


update bolsos.mg_image
set real_name = substring(real_name from 7 for length(real_name))
where real_name like '%/mgweb%'
