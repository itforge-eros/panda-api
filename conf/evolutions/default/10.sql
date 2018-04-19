# --- !Ups

CREATE TABLE material (
  id                    uuid PRIMARY KEY,
  name                  json NOT NULL,
  department_id         uuid NOT NULL REFERENCES department (id)
);

# --- !Downs

DROP TABLE material;
