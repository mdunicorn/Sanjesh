CREATE TABLE universityagent_aud
(
  universityagent_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  organizationcode character varying(255),
  birthdate date,
  birthlocation character varying(255),
  suser_ref integer not null,
  CONSTRAINT universityagent_aud_pkey PRIMARY KEY (universityagent_id, rev),
  CONSTRAINT universityagent_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);