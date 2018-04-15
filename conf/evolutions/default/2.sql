# --- !Ups

CREATE DOMAIN positive_int AS INT
  CHECK(VALUE >= 0);

CREATE TABLE space (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text,
  capacity              positive_int,
  is_available          boolean NOT NULL,
  created_at            timestamp NOT NULL,
  department_id         uuid NOT NULL references department (id),

  UNIQUE (name, department_id)
);

# --- !Downs

DROP TABLE space;

DROP DOMAIN positive_int;
