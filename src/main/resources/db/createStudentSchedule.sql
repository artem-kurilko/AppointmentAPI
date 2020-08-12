create table student_schedule(id serial primary key,
                              student_name character varying(100) not null,
                              schedule json not null);