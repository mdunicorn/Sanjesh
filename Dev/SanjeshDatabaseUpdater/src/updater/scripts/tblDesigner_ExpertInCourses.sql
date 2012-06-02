CREATE TABLE designer_expertincourses
(
  designer_expertincourses_id serial NOT NULL,
  designer_ref integer NOT NULL,
  course_ref integer NOT NULL,
  start_date date,
  end_date date,
  CONSTRAINT designer_expertincourses_pkey PRIMARY KEY (designer_expertincourses_id),
  CONSTRAINT fkey_designer_expertincourses_designer_ref FOREIGN KEY (designer_ref)
      REFERENCES designer (designer_id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fkey_designer_expertincourses_course_ref FOREIGN KEY (course_ref)
      REFERENCES course (course_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
);
