-- For H2 Database
create table farmers (
  id bigserial not null primary key,
  name varchar(512) not null,
  maister_farmer_id int not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
