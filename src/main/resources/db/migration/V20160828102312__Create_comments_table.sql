-- For H2 Database
create table comments (
  id bigserial not null primary key,
  comment_body varchar(512),
  crop_id int not null,
  process_id int,
  consumer_id int,
  farmer_id int,
  post_time timestamp not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
