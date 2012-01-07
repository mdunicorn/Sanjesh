CREATE TABLE course
(
  course_id SERIAL NOT NULL,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  educationfield_ref integer NOT NULL,
  CONSTRAINT course_pkey PRIMARY KEY (course_id),
  CONSTRAINT fkey_course_educationfield FOREIGN KEY (educationfield_ref)
      REFERENCES educationfield (educationfield_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);