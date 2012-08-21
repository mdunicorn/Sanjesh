CREATE TABLE question_evaluation_aud
(
  question_evaluation_id serial NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  question_ref integer NOT NULL,
  arbiter_ref integer NOT NULL,
  "result" integer NOT NULL,
  reason character varying(4000),
  answer_time integer NOT NULL,
  comments character varying(4000),
  CONSTRAINT question_evaluation_aud_pkey PRIMARY KEY (question_evaluation_id, rev),
  CONSTRAINT question_evaluation_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

