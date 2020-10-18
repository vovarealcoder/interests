CREATE TABLE IF NOT EXISTS public.picture
(
    id                bigserial               NOT NULL,
    picture_object_id bigint                  NOT NULL,
    filename          character varying(1000) NOT NULL,
    type              integer                 NOT NULL,
    width             integer                 NOT NULL,
    height            integer                 NOT NULL,
    CONSTRAINT picture_pkey PRIMARY KEY (id),
    CONSTRAINT pic_objc_fk FOREIGN KEY (picture_object_id)
        REFERENCES public.picture_object (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)