-- create database aguripad;
-- GRANT ALL ON ALL TABLES IN SCHEMA public TO aguripad;

create table farmers (
  id serial PRIMARY KEY,
  name varchar(127) not null,
  maister_farmer_id int,
  created_at timestamp not null,
  updated_at timestamp not null
);

create table crops (
  id serial PRIMARY KEY,
  crop_name varchar(255) not null,
  title varchar(1023) not null,
  sales_comment text not null default 0,
  good_points bigint not null default 0,
  product_image1 varchar(4095),
  product_image2 varchar(4095),
  product_image3 varchar(4095),
  post_time timestamp not null,
  created_at timestamp not null,
  updated_at timestamp not null
);

create table processes (
  id serial PRIMARY KEY,
  crop_id int not null,
  image_url varchar(4095),
  comment text,
  particular smallint not null default 0,
  post_date date default current_date,
  post_time timestamp,
  created_at timestamp not null,
  updated_at timestamp not null
);

create table comments (
  id serial PRIMARY KEY,
  comment_body text,
  crop_id int not null,
  process_id bigint,
  consumer_id int,
  farmer_id int,
  post_time timestamp,
  created_at timestamp not null,
  updated_at timestamp not null
);

create table consumers (
id serial PRIMARY KEY,
  name text,
  created_at timestamp not null,
  updated_at timestamp not null
);

create table fans (
  id serial PRIMARY KEY,
  consumer_id int not null,
  farmer_id int not null,
  created_at timestamp not null,
  updated_at timestamp not null
);

CREATE INDEX idx_comment_crop_id ON comments (crop_id);
CREATE INDEX idx_process_post_date ON processes (post_date);
CREATE INDEX idx_fan_consumer_id ON fans (consumer_id);
CREATE INDEX idx_fan_farmer_id ON fans (farmer_id);



