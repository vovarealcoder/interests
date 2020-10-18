CREATE TABLE IF NOT EXISTS public.refresh_token
(
    id          bigserial                NOT NULL,
    login       character varying(100)   NOT NULL UNIQUE,
    token       character varying(1000)  NOT NULL,
    created     timestamp with time zone NOT NULL default now(),
    last_refresh timestamp with time zone NOT NULL default now()
)