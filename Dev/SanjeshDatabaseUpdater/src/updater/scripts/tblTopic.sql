CREATE TABLE topic
(
  topic_id integer NOT NULL,
  "name" character varying(255) NOT NULL,
  course_ref integer NOT NULL,
  CONSTRAINT topic_pkey PRIMARY KEY (topic_id),
  CONSTRAINT fkey_topic_course FOREIGN KEY (course_ref)
      REFERENCES course (course_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);