
--Standarize collection name removing blank spaces by under line
update bolsos.mg_image
set name = replace(name, ' ', '_')
where id in (1772,
1776,
1778,
1780,
1782,
1784,
1786,
1788,
1790,
1792,
2766);

update bolsos.mg_image
set real_name = replace(real_name, ' ', '_')
where id in (1772,
1776,
1778,
1780,
1782,
1784,
1786,
1788,
1790,
1792,
2766);