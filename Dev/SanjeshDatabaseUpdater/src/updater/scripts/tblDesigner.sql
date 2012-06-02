CREATE TABLE designer
(
  designer_id SERIAL NOT NULL,
  "name" character varying(255),
  "family" character varying(255) NOT NULL,
  organizationcode character varying(255),
  emailaddress character varying(255),
  birthdate date,
  birthlocation character varying(255),
  grade_ref integer,
  registerstate integer,
  suser_ref integer NOT NULL,

  id_number character varying(255),
  id_issue_location character varying(255),
  national_code character varying(255),
  phone_home character varying(255),
  phone_cell character varying(255),
  educationfield_ref integer,
  educationfield_other character varying(255),
  last_degree character varying(255),
  degree_university_ref integer,
  degree_university_other character varying(255),
  home_address character varying(255),
  zip_code character varying(255),

  work_university_ref integer,
  work_university_other character varying(255),
  work_startdate date,
  faculty character varying(255),
  educationgroup_ref integer,
  educationgroup_other character varying(255),
  phone_work character varying(255),
  fax_work character varying(255),
  work_position character varying(255),
  position_startdate date,
  position_enddate date,

  CONSTRAINT designer_pkey PRIMARY KEY (designer_id),
  CONSTRAINT fkey_designer_grade FOREIGN KEY (grade_ref)
      REFERENCES grade (grade_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_designer_suser_ref FOREIGN KEY (suser_ref)
      REFERENCES suser (suser_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkey_designer_educationfield_ref FOREIGN KEY (educationfield_ref)
      REFERENCES educationfield (educationfield_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_designer_work_university_ref FOREIGN KEY (work_university_ref)
      REFERENCES university (university_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT fkey_designer_educationgroup_ref FOREIGN KEY (educationgroup_ref)
      REFERENCES educationgroup (educationgroup_id)
      ON UPDATE NO ACTION ON DELETE RESTRICT
);