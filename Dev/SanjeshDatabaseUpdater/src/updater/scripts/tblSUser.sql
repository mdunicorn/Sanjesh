CREATE TABLE SUser
(
  suser_id SERIAL NOT NULL,
  username character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  CONSTRAINT suer_pkey PRIMARY KEY (suser_id)
)
WITH (
  OIDS=FALSE
);