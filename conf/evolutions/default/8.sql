# --- !Ups

CREATE TABLE role (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text,
  permissions           varchar(64)[] NOT NULL,
  created_at            timestamp NOT NULL,
  updated_at            timestamp NOT NULL,
  department_id         uuid NOT NULL REFERENCES department (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX unique_role_name
  ON role (department_id, lower(name));

# --- !Downs

DROP TABLE role;
