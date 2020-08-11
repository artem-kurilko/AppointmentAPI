create table teacher_schedule(id serial primary key,
                              teacher_name character varying(100) not null unique,
                              schedule jsonb not null);