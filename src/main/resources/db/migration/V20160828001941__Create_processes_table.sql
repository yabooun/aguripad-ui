-- For H2 Database
create table processes (
  id bigserial not null primary key,
  topic_id int not null,
  post_time timestamp not null,
  image_url varchar(512) not null,
  text varchar(512) not null,
  particular int not null,
  sensor_json varchar(512) not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
