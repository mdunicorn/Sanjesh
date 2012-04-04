CREATE TABLE question_aud
(
  question_id integer NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  designer_ref integer NOT NULL,
  course_ref integer NOT NULL,
  question_text text,
  question_image bytea,
  taught boolean NOT NULL DEFAULT false,
  answertime integer,
  questionlevel integer,
  answer_text character varying(4000),
  answer_image bytea,
  incorrect_option1_text character varying(4000),
  incorrect_option1_image bytea,
  incorrect_option2_text character varying(4000),
  incorrect_option2_image bytea,
  incorrect_option3_text character varying(4000),
  incorrect_option3_image bytea,
  designdate date,
  registerstate integer,
  CONSTRAINT question_aud_pkey PRIMARY KEY (question_id, rev),
  CONSTRAINT question_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

