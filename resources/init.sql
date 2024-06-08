--drop table application.ticket;
--drop table application.event;
--drop table application.place;
--drop table application.event_type;
--drop schema application;

create schema application;

create table application.event_type(id serial primary key,
                                    name varchar(100));

insert into application.event_type (name) values('museum');
insert into application.event_type (name) values('cinema');
insert into application.event_type (name) values('theatre');

create table application.place(id serial primary key,
                               name varchar(100) not null,
                               address varchar(100) not null,
                               city varchar(100) not null);

create table application.event(id serial primary key,
                               name varchar(100) not null,
                               event_type_id int references application.event_type(id),
                               event_date timestamp not null,
                               place_id int references application.place(id));

create table application.ticket(id serial primary key,
                                event_id int references application.event(id),
                                client_email varchar(100) not null,
                                price numeric not null,
                                is_sold boolean);
