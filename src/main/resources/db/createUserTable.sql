create table university_user(id serial primary key,
                  user_type character varying(10) not null,
                  user_name character varying(100) not null,
                  user_email character varying(100) unique not null);