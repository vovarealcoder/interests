CREATE TABLE IF NOT EXISTS public.user
(
    id            bigserial                NOT NULL,
    login         character varying(100)   NOT NULL UNIQUE,
    password      character varying(100)   NOT NULL,
    firstname     character varying(500)   NOT NULL,
    lastname      character varying(500)   NOT NULL,
    gender        integer,
    active        boolean                  NOT NULL default false,
    birthdate     date,
    regtime       timestamp with time zone NOT NULL default now(),
    about         text,
    avatar_pic_id bigint,
    town_id       bigint,
    CONSTRAINT avatart_fk FOREIGN KEY (avatar_pic_id)
        REFERENCES picture_object (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT town_fk FOREIGN KEY (town_id)
        REFERENCES town (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)