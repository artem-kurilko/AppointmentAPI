create table student_schedule(id serial primary key,
                              student_name character varying(100) not null,
                              appointment_date timestamptz not null,
                              appointment_finish_date timestamptz not null,
                              teacher_name character varying(100) not null,
                              status character varying(20));