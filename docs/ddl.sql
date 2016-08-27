create database aguripad;

create table farmer (
  id sereial PRIMARY KEY,
    name varchar(127) not null,
      master_farmer_id int
      );

create table crop (
  id sereial PRIMARY KEY,
    crop_name varchar(255) not null,
      title varchar(1023) not null,
        sales_comment text not null default 0,
	  good_point bigint not null default 0,
	    product_image1 varchar(4095),
	      product_image2 varchar(4095),
	        product_image3 varchar(4095),
		  post_time timestamp default current_timestamp
		  );

create table process (
  id sereial PRIMARY KEY,
    crop_id int not null,
      image_url varchar(4095),
        comment text,
	  particular tinyint not null default 0,
	    sensor_json JSON,
	      post_date date default current_date,
	        post_time timestamp default current_timestamp
		);
CREATE INDEX idx_process_post_date ON process (post_date);

create table comment (
  id serial PRIMARY KEY,
    comment_body text,
      crop_id int not null,
        process_id bigint not null,
	  consumer_id int not null,
	    farmer_id int not null
	      post_time timestamp default currnet_timestamp
	      );
CREATE INDEX idx_comment_crop_id ON comment (crop_id);

create table consumer (
  id serial PRIMARY KEY,
    name text
    );

create table fan (
  id serial PRIMARY KEY,
    consumer_id int not null,
      farmer_id int not null
      );

CREATE INDEX idx_fan_consumer_id ON fan (consumer_id);
CREATE INDEX idx_fan_farmer_id ON fan (farmer_id);



