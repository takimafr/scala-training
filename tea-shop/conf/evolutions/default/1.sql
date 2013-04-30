# --- First database schema

# --- !Ups

set ignorecase true;

create table user (
  login						varchar(255) not null,
  password					varchar(255) not null,
  name						varchar(255) not null,
  constraint pk_user primary key (login));

create table supplier (
  id                        int not null,
  name                      varchar(255) not null,
  country					varchar(255) not null,
  uri						varchar(255) not null,
  constraint pk_supplier primary key (id))
;

create table tea (
  id                        int not null,
  name                      varchar(255) not null,
  color                		varchar(25) not null,
  size                		varchar(25) not null,
  currency					varchar(1) not null,
  price						double,
  supplier_id				int not null,
  constraint pk_tea primary key (id))
;

create sequence supplier_seq start with 1000;

create sequence tea_seq start with 1000;

alter table tea add constraint fk_tea_supplier_1 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_tea_supplier_1 on tea (supplier_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists supplier;

drop table if exists tea;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists supplier_seq;

drop sequence if exists tea_seq;

