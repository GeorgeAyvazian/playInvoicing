# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table billers (
  id                        bigint not null,
  constraint pk_billers primary key (id))
;

create table customers (
  id                        bigint not null,
  constraint pk_customers primary key (id))
;

create table invoices (
  id                        bigint not null,
  constraint pk_invoices primary key (id))
;

create table products (
  id                        bigint not null,
  description               varchar(255) not null,
  unitprice                 bigint,
  tax_id                    bigint not null,
  constraint pk_products primary key (id))
;

create table tax_rates (
  id                        bigint not null,
  description               varchar(255),
  amount                    bigint not null,
  constraint pk_tax_rates primary key (id))
;

create sequence billers_seq;

create sequence customers_seq;

create sequence invoices_seq;

create sequence products_seq;

create sequence tax_rates_seq;

alter table products add constraint fk_products_tax_1 foreign key (tax_id) references tax_rates (id);
create index ix_products_tax_1 on products (tax_id);



# --- !Downs

drop table if exists billers cascade;

drop table if exists customers cascade;

drop table if exists invoices cascade;

drop table if exists products cascade;

drop table if exists tax_rates cascade;

drop sequence if exists billers_seq;

drop sequence if exists customers_seq;

drop sequence if exists invoices_seq;

drop sequence if exists products_seq;

drop sequence if exists tax_rates_seq;

