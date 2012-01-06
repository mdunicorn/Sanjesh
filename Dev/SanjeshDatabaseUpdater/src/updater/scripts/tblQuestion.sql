CREATE TABLE question
(
  question_id integer NOT NULL,
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
  CONSTRAINT question_pkey PRIMARY KEY (question_id),
  CONSTRAINT fkey_question_designer FOREIGN KEY (designer_ref)
      REFERENCES designer (designer_id)
      ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fkey_question_course FOREIGN KEY (course_ref)
      REFERENCES course (course_id)
      ON UPDATE CASCADE ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

