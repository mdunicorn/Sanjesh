CREATE TABLE educationgroup_aud
(
  educationgroup_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  CONSTRAINT educationgroup_aud_pkey PRIMARY KEY (educationgroup_id, rev),
  CONSTRAINT educationgroup_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);