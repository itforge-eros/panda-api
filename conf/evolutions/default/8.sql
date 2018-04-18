# --- !Ups

CREATE TABLE role (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text,
  permissions           varchar(64)[] NOT NULL,
  department_id         uuid NOT NULL REFERENCES department (id)
);

CREATE UNIQUE INDEX unique_role_name
  ON space (id, lower(name));

# --- !Downs

DROP TABLE role;
