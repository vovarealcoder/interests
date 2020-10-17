CREATE TABLE IF NOT EXISTS public.category
(
    id          bigserial               not null,
    name        character varying(100)  NOT NULL,
    description character varying(3000) NOT NULL,
    parent_id   bigint,
    picture_id  bigint,
    CONSTRAINT category_pkey PRIMARY KEY (id),
    CONSTRAINT parent_fk FOREIGN KEY (parent_id)
        REFERENCES public.category (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT picture_ref_fk FOREIGN KEY (picture_id)
        REFERENCES public.picture_object (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)