create table teacher_schedule(id serial primary key,
                              teacher_name character varying(100) not null,
                              appointment_date timestamptz not null,
                              appointment_finish_date timestamptz not null,
                              students text[]);