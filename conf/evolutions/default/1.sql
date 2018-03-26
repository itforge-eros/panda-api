# --- !Ups

CREATE DOMAIN positive_int AS INT
  CHECK(VALUE >= 0);

CREATE TABLE space (
  id                    UUID PRIMARY KEY,
  name                  VARCHAR(64) NOT NULL,
  description           TEXT,
  capacity              positive_int NOT NULL,
  required_approval     positive_int NOT NULL,
  is_reservable         BOOLEAN NOT NULL
);

# ---!Downs

DROP TABLE space
