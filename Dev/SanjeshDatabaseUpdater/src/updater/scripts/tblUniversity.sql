CREATE TABLE university
(
  university_id integer NOT NULL,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  address character varying(4000),
  phone character varying(255),
  CONSTRAINT university_pkey PRIMARY KEY (university_id)
)
WITH (
  OIDS=FALSE
);