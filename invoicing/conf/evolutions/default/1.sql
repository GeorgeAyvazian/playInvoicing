# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table invoices (
  id                        bigint not null,
  number                    varchar(255),
  constraint pk_invoices primary key (id))
;

create table line_items (
  id                        bigint not null,
  product_id                bigint not null,
  quantity                  integer,
  amount                    bigint,
  invoice_id                bigint,
  constraint pk_line_items primary key (id))
;

create table products (
  id                        bigint not null,
  description               varchar(255) not null,
  unit_price                bigint,
  tax_id                    bigint not null,
  constraint pk_products primary key (id))
;

create table tax_rates (
  id                        bigint not null,
  description               varchar(255),
  amount                    bigint not null,
  constraint pk_tax_rates primary key (id))
;

create sequence invoices_seq;

create sequence line_items_seq;

create sequence products_seq;

create sequence tax_rates_seq;

alter table line_items add constraint fk_line_items_product_1 foreign key (product_id) references products (id);
create index ix_line_items_product_1 on line_items (product_id);
alter table line_items add constraint fk_line_items_invoice_2 foreign key (invoice_id) references invoices (id);
create index ix_line_items_invoice_2 on line_items (invoice_id);
alter table products add constraint fk_products_tax_3 foreign key (tax_id) references tax_rates (id);
create index ix_products_tax_3 on products (tax_id);



# --- !Downs

drop table if exists invoices cascade;

drop table if exists line_items cascade;

drop table if exists products cascade;

drop table if exists tax_rates cascade;

drop sequence if exists invoices_seq;

drop sequence if exists line_items_seq;

drop sequence if exists products_seq;

drop sequence if exists tax_rates_seq;

