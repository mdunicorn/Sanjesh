CREATE TABLE suser_role_aud
(
  suser_ref integer NOT NULL,
  role_ref integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  CONSTRAINT suser_role_aud_pkey PRIMARY KEY (suser_ref, role_ref, rev),
  CONSTRAINT suser_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
