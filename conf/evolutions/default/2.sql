# --- !Ups

CREATE TABLE member (
  id                    UUID PRIMARY KEY,
  first_name            VARCHAR(64) NOT NULL,
  last_name             VARCHAR(64) NOT NULL,
  email                 VARCHAR(40) NOT NULL
);

# ---!Downs

DROP TABLE member
