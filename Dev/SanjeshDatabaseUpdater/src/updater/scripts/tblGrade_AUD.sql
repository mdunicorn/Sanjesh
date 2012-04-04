CREATE TABLE grade_aud
(
  grade_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255) NOT NULL,
  CONSTRAINT grade_aud_pkey PRIMARY KEY (grade_id, rev),
  CONSTRAINT grade_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);