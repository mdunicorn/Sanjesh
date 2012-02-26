CREATE TABLE suser_role
(
  suser_ref integer NOT NULL,
  role_ref integer NOT NULL,
  CONSTRAINT suser_role_pkey PRIMARY KEY (suser_ref, role_ref),
  CONSTRAINT fkey_suser_role_role_ref FOREIGN KEY (role_ref)
      REFERENCES "role" (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fkey_suser_role_suser_ref FOREIGN KEY (suser_ref)
      REFERENCES suser (suser_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
