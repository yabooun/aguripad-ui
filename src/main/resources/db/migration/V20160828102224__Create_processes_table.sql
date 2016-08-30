-- For H2 Database
create table processes (
  id bigserial not null primary key,
  crop_id int not null,
  post_date timestamp not null,
  post_time timestamp not null,
  image_url varchar(512),
  comment varchar(512),
  particular int not null,
  sensor_json varchar(512),
  created_at timestamp not null,
  updated_at timestamp not null
)
