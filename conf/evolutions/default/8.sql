# --- !Ups

CREATE TABLE department (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text
);

ALTER TABLE space ADD department_id uuid REFERENCES department (id);

# --- !Downs

ALTER TABLE space DROP department_id;

DROP TABLE department;
