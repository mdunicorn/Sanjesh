CREATE TABLE university_aud
(
  university_id SERIAL NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  address character varying(4000),
  phone character varying(255),
  CONSTRAINT university_aud_pkey PRIMARY KEY (university_id, rev),
  CONSTRAINT university_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);