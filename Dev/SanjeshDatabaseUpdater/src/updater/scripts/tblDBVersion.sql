CREATE TABLE dbversion
(
  dbversion_id SERIAL NOT NULL,
  major INTEGER NOT NULL,
  minor INTEGER NOT NULL,
  insertdate TIMESTAMP,
  CONSTRAINT fkey_dbversion PRIMARY KEY (dbversion_id)
);