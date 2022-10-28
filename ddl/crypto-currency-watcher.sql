CREATE SCHEMA app;

CREATE TABLE app.coin (
    id bigint NOT NULL,
    price_usd numeric NOT NULL,
    symbol character varying NOT NULL
);

CREATE SEQUENCE app.coin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE app.user_notification (
    id bigint NOT NULL,
    username character varying NOT NULL,
    symbol character varying NOT NULL,
    start_price numeric NOT NULL,
    enabled boolean NOT NULL
);

CREATE SEQUENCE app.user_notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY app.coin ALTER COLUMN id SET DEFAULT nextval('app.coin_id_seq'::regclass);

ALTER TABLE ONLY app.user_notification ALTER COLUMN id SET DEFAULT nextval('app.user_notification_id_seq'::regclass);

ALTER TABLE ONLY app.coin
    ADD CONSTRAINT coin_pkey PRIMARY KEY (id);

ALTER TABLE ONLY app.coin
    ADD CONSTRAINT symbol_unique UNIQUE (symbol);

ALTER TABLE ONLY app.user_notification
    ADD CONSTRAINT user_notification_pkey PRIMARY KEY (id);

ALTER TABLE ONLY app.user_notification
    ADD CONSTRAINT symbol_fk FOREIGN KEY (symbol) REFERENCES app.coin(symbol) NOT VALID;
