CREATE TABLE arbiter_aud
(
  arbiter_id SERIAL NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  national_code character varying(255),
  educationgroup_ref integer NOT NULL,
  suser_ref int NOT NULL,

  CONSTRAINT arbiter_aud_pkey PRIMARY KEY (arbiter_id, rev),
  CONSTRAINT arbiter_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
