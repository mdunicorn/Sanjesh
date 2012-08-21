CREATE TABLE arbiter
(
  arbiter_id SERIAL NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  emailaddress character varying(255),
  national_code character varying(255),
  educationgroup_ref integer NOT NULL,
  suser_ref int NOT NULL,
  CONSTRAINT arbiter_pkey PRIMARY KEY (arbiter_id),
  CONSTRAINT fkey_arbiter_educationgroup_ref FOREIGN KEY (educationgroup_ref)
      REFERENCES educationgroup (educationgroup_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_arbiter_suser_ref FOREIGN KEY (suser_ref)
      REFERENCES suser (suser_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
);
