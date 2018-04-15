# --- !Ups

CREATE TABLE role (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text,
  permissions           varchar(64)[] NOT NULL,
  department_id         uuid NOT NULL REFERENCES department (id)
);

# --- !Downs

DROP TABLE role;
