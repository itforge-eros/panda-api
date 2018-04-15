# --- !Ups

CREATE TABLE department (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) UNIQUE NOT NULL,
  description           text
);

# --- !Downs

DROP TABLE department
