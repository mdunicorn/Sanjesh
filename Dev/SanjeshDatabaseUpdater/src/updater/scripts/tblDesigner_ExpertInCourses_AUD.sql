CREATE TABLE designer_expertincourses_aud
(
  designer_expertincourses_id integer NOT NULL,
  designer_ref integer NOT NULL,
  course_ref integer NOT NULL,
  start_date date,
  end_date date,
  rev integer NOT NULL,
  revtype smallint,
  CONSTRAINT designer_expertincourses_aud_pkey PRIMARY KEY (designer_expertincourses_id, rev),
  CONSTRAINT designer_expertincourses_aud_rev_fkey FOREIGN KEY (rev)
      REFERENCES revinfo (rev)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
