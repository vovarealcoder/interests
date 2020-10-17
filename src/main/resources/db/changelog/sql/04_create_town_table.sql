CREATE TABLE IF NOT EXISTS public.town
(
    id   bigserial              NOT NULL,
    name character varying(100) NOT NULL UNIQUE
)