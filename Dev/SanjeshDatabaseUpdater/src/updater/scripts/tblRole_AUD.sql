CREATE TABLE role_aud
(
  role_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255) NOT NULL,
  fixed boolean NOT NULL,
  CONSTRAINT role_aud_pkey PRIMARY KEY (role_id, rev),
  CONSTRAINT role_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
