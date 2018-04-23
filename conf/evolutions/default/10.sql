# --- !Ups

CREATE TABLE material (
  id                    uuid PRIMARY KEY,
  name                  json NOT NULL,
  created_at            timestamp NOT NULL,
  department_id         uuid NOT NULL REFERENCES department (id) ON DELETE CASCADE
);

# --- !Downs

DROP TABLE material;
