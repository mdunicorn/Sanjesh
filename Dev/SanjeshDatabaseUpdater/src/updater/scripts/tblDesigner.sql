CREATE TABLE designer
(
  designer_id SERIAL NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  --nationalcode character varying(255),
  organizationcode character varying(255),
  emailaddress character varying(255),
  birthdate date,
  birthlocation character varying(255),
  grade_ref integer,
  registerstate integer,
  suser_ref integer NOT NULL,
  CONSTRAINT designer_pkey PRIMARY KEY (designer_id),
  CONSTRAINT fkey_designer_grade FOREIGN KEY (grade_ref)
      REFERENCES grade (grade_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_designer_suser_ref FOREIGN KEY (suser_ref)
      REFERENCES suser (suser_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);