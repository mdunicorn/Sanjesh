CREATE TABLE question_evaluation
(
  question_evaluation_id serial NOT NULL,
  question_ref integer NOT NULL,
  arbiter_ref integer NOT NULL,
  "result" integer NOT NULL,
  reason character varying(4000),
  answer_time integer NOT NULL,
  comments character varying(4000),
  CONSTRAINT question_evaluation_pkey PRIMARY KEY (question_evaluation_id),
  CONSTRAINT fkey_question_evaluation_question_ref FOREIGN KEY (question_ref)
      REFERENCES question (question_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_question_evaluation_arbiter_ref FOREIGN KEY (arbiter_ref)
      REFERENCES arbiter (arbiter_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
);

