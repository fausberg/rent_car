 create sequence cars_s start 1 increment 1;

 create sequence credit_card_id_seq start 1 increment 1;

 create sequence driver_licences_s start 1 increment 1;

 create sequence fines_s start 1 increment 1;

create sequence rent_history_s start 1 increment 1;

create sequence users_s start 1 increment 1;

create sequence images_id_seq start 1 increment 1;

 create table public.cars (
                              id integer primary key not null default nextval('cars_s'::regclass),
                              booking boolean,
                              color character varying(255),
                              manufacturer character varying(255),
                              model character varying(255),
                              number character varying(255),
                              price double precision
 );

 create table public.credit_cards (
                                      id integer primary key not null default nextval('credit_card_id_seq'::regclass),
                                      cvv character varying(255),
                                      card_number character varying(255),
                                      date character varying(255),
                                      user_id integer,
                                      foreign key (user_id) references public.users (id)
                                          match simple on update cascade on delete cascade
 );

 create table public.driver_licences (
                                         id integer primary key not null default nextval('driver_licences_s'::regclass),
                                         expire date,
                                         issued date,
                                         user_id integer,
                                         foreign key (user_id) references public.users (id)
                                             match simple on update cascade on delete cascade
 );

 create table public.fines (
                               id integer primary key not null default nextval('fines_s'::regclass),
                               car_id integer,
                               date date,
                               description character varying(255),
                               fee double precision,
                               user_id integer,
                               foreign key (car_id) references public.cars (id)
                                   match simple on update cascade on delete cascade,
                               foreign key (user_id) references public.users (id)
                                   match simple on update cascade on delete cascade
 );

 create table public.rent_history (
                                      id integer primary key not null default nextval('rent_history_s'::regclass),
                                      car_id integer,
                                      cost double precision,
                                      time_end timestamp without time zone,
                                      time_start timestamp without time zone,
                                      user_id integer,
                                      foreign key (car_id) references public.cars (id)
                                          match simple on update cascade on delete cascade,
                                      foreign key (user_id) references public.users (id)
                                          match simple on update cascade on delete cascade
 );

 create table public.users (
                               id integer primary key not null default nextval('users_s'::regclass),
                               email character varying(255),
                               first_name character varying(8),
                               last_name character varying(10),
                               login character varying(255),
                               password character varying(255),
                               phone_number character varying(255),
                               rent_history_start_time timestamp without time zone,
                               role character varying(255)
 );
 create table public.images (
                                id integer primary key not null default nextval('images_id_seq'::regclass),
                                driver_licence_id integer,
                                name character varying,
                                data bytea,
                                foreign key (driver_licence_id) references public.driver_licences (id)
                                    match simple on update no action on delete no action
 );