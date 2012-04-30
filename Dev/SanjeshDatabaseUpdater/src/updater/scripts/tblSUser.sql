CREATE TABLE suser
(
  suser_id SERIAL NOT NULL,
  username character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  isactive boolean NOT NULL DEFAULT true,
  CONSTRAINT suer_pkey PRIMARY KEY (suser_id)
)
WITH (
  OIDS=FALSE
);