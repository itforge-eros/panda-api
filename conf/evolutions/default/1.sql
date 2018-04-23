# --- !Ups

CREATE TABLE department (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  full_english_name     varchar(64) NOT NULL,
  full_thai_name        varchar(64) NOT NULL,
  description           text,
  created_at            timestamp NOT NULL,
  updated_at            timestamp NOT NULL
);

CREATE UNIQUE INDEX unique_department_name
  ON department (lower(name));

# --- !Downs

DROP TABLE department;
