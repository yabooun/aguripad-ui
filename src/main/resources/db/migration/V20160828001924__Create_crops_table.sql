-- For H2 Database
create table crops (
  id bigserial not null primary key,
  crop_name varchar(512) not null,
  title varchar(512) not null,
  sales_text varchar(512) not null,
  good_points bigint not null,
  product_image1 varchar(512) not null,
  product_image2 varchar(512) not null,
  product_image3 varchar(512) not null,
  post_time timestamp not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
