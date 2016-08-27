-- For H2 Database
create table comments (
  id bigserial not null primary key,
  comment_body varchar(512) not null,
  crop_id int not null,
  process_id int not null,
  consumer_id int not null,
  farmer_id int not null,
  post_time timestamp not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
