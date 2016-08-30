-- For H2 Database
create table fans (
  id bigserial not null primary key,
  consumer_id int not null,
  farmer_id int not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
