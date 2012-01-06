CREATE TABLE universityagent
(
  universityagent_id integer NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  organizationcode character varying(255),
  birthdate date,
  birthlocation character varying(255),
  CONSTRAINT universityagent_pkey PRIMARY KEY (universityagent_id)
)
WITH (
  OIDS=FALSE
);