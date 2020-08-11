create table teacher_rate(id serial primary key,
                          teacher_name character varying(100) not null,
                          rate jsonb not null);