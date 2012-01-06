CREATE TABLE educationfield
(
  educationfield_id integer NOT NULL,
  code character varying(50) NOT NULL,
  "name" character varying(255) NOT NULL,
  educationgroup_ref integer NOT NULL,
  CONSTRAINT educationfield_pkey PRIMARY KEY (educationfield_id),
  CONSTRAINT fkey_educationfield_educationgroup FOREIGN KEY (educationgroup_ref)
      REFERENCES educationgroup (educationgroup_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);