create table teacher_rate(id serial primary key,
                          teacher_name character varying(100) not null,
                          time integer not null,
                          price integer not null);