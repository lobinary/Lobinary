create table card_record
(
  id int generated always as identity,
  owner int,
  card_id int,
  used smallint,
  send_reason varchar(255),
  use_reason varchar(255),
  create_time timestamp,
  start_time timestamp,
  end_time timestamp,
  use_time timestamp
)

create table CARD_INFO
(
id int generated always as identity,
name varchar(50),
rules varchar(255),
productorId int,
cardStatus varchar(20),
backgroundColor varchar(50)
)