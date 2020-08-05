create table teacher_schedule(id serial primary key,
                              teacher_name character varying(100) not null unique,
                              appointment_date timestamp not null,
                              appointment_finish_date timestamp not null);