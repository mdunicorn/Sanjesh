CREATE TABLE revinfo
(
  rev serial NOT NULL,
  "timestamp" bigint,
  suser integer NOT NULL,
  CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
)
WITH (
  OIDS=FALSE
);