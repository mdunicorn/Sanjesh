CREATE TABLE sanjeshagent
(
  sanjeshagent_id integer NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  organizationcode character varying(255),
  birthdate date,
  birthlocation character varying(255),
  IsQuestionExpert boolean,
  IsArbiterExpert boolean,
  CONSTRAINT sanjeshagent_pkey PRIMARY KEY (sanjeshagent_id)
)
WITH (
  OIDS=FALSE
);