CREATE TABLE designer_expertincourses
(
  designer_ref integer NOT NULL,
  course_ref integer NOT NULL,
  CONSTRAINT designer_expertincourses_pkey PRIMARY KEY (designer_ref, course_ref),
  CONSTRAINT fkey_designer_expertincourses_designer_ref FOREIGN KEY (designer_ref)
      REFERENCES designer (designer_id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fkey_designer_expertincourses_course_ref FOREIGN KEY (course_ref)
      REFERENCES course (course_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
);
