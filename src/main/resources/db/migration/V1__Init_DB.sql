Hibernate: create sequence cars_s start 1 increment 1

Hibernate: create sequence credit_card_id_seq start 1 increment 1

Hibernate: create sequence driver_licences_s start 1 increment 1

Hibernate: create sequence fines_s start 1 increment 1

Hibernate: create sequence rent_history_s start 1 increment 1

Hibernate: create sequence users_s start 1 increment 1

Hibernate: create table cars (
    id int4 not null,
    booking boolean,
    color varchar(255) not null,
    manufacturer varchar(255) not null,
    model varchar(255) not null,
    number varchar(255) not null,
    price float8 not null,
    primary key (id)
);

Hibernate: create table credit_cards (
    id int4 not null,
    cvv varchar(255) not null,
    card_number varchar(255) not null,
    date varchar(255) not null,
    user_id int4 not null,
    primary key (id)
);

Hibernate: create table driver_licences (
    id int4 not null,
    expire date not null,
    issued date not null,
    primary key (id)
);

Hibernate: create table fines (
    id int4 not null,
    car_id varchar(255) not null,
    date date not null,
    description varchar(255) not null,
    fee float8 not null,
    user_id varchar(255) not null,
    primary key (id)
);

Hibernate: create table rent_historyes (
    id int4 not null,
    car_id int4 not null,
    cost float8 not null,
    time_end timestamp not null,
    time_start timestamp not null,
    user_id int4 not null,
    primary key (id)
);

Hibernate: create table users (
    id int4 not null,
    email varchar(255),
    first_name varchar(8) not null,
    last_name varchar(10) not null,
    login varchar(255) not null,
    password varchar(255) not null,
    phone_number varchar(255),
    rent_history_start_time timestamp,
    role varchar(255),
    primary key (id)
);
