CREATE TABLE topic_aud
(
  topic_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  "name" character varying(255) NOT NULL,
  course_ref integer NOT NULL,
  CONSTRAINT topic_aud_pkey PRIMARY KEY (topic_id, rev),
  CONSTRAINT topic_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);