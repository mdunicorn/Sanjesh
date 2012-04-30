CREATE TABLE suser_aud
(
  suser_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  username character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  isactive boolean NOT NULL DEFAULT true,
  CONSTRAINT suer_aud_pkey PRIMARY KEY (suser_id, rev),
  CONSTRAINT suer_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);