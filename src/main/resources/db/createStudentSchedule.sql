create table student_schedule(id serial primary key,
                              student_name character varying(100) not null,
                              appointment_date timestamp not null,
                              appointment_finish_date timestamp not null,
                              appointment_status character varying(10) not null);