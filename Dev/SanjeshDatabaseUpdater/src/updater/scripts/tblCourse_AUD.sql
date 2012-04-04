CREATE TABLE course_aud
(
  course_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  educationfield_ref integer NOT NULL,
  CONSTRAINT course_aud_pkey PRIMARY KEY (course_id, rev),
  CONSTRAINT course_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);