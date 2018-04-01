# --- !Ups

CREATE DOMAIN positive_int AS INT
  CHECK(VALUE >= 0);

CREATE TABLE space (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text,
  capacity              positive_int NOT NULL,
  is_available          boolean NOT NULL,
  created_at            timestamp NOT NULL
);

# ---!Downs

DROP TABLE space;
