CREATE TABLE grade
(
  grade_id integer NOT NULL,
  "name" character varying(255) NOT NULL,
  CONSTRAINT grade_pkey PRIMARY KEY (grade_id)
)
WITH (
  OIDS=FALSE
);