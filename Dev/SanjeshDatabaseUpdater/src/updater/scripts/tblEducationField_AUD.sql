CREATE TABLE educationfield_aud
(
  educationfield_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  educationgroup_ref integer NOT NULL,
  CONSTRAINT educationfield_aud_pkey PRIMARY KEY (educationfield_id, rev),
  CONSTRAINT educationfield_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);