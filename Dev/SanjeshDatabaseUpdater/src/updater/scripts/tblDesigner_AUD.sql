CREATE TABLE designer_aud
(
  designer_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  --nationalcode character varying(255),
  organizationcode character varying(255),
  emailaddress character varying(255),
  birthdate date,
  birthlocation character varying(255),
  grade_ref integer,
  registerstate integer,
  CONSTRAINT designer_aud_pkey PRIMARY KEY (designer_id, rev),
  CONSTRAINT designer_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);