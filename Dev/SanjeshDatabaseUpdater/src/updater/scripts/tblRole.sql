CREATE TABLE "role"
(
  role_id serial NOT NULL,
  "name" character varying(255) NOT NULL,
  fixed boolean NOT NULL DEFAULT false,
  CONSTRAINT role_pkey PRIMARY KEY (role_id)
)
WITH (
  OIDS=FALSE
);
