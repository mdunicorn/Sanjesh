CREATE TABLE universityagent
(
  universityagent_id SERIAL NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  organizationcode character varying(255),
  birthdate date,
  birthlocation character varying(255),
  suser_ref integer not null,
  CONSTRAINT universityagent_pkey PRIMARY KEY (universityagent_id),
  CONSTRAINT fkey_universityagent_suser_ref FOREIGN KEY (suser_ref)
      REFERENCES suser (suser_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);