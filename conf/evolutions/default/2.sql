# --- !Ups

CREATE TABLE member (
  id                    uuid PRIMARY KEY,
  first_name            varchar(64) NOT NULL,
  last_name             varchar(64) NOT NULL,
  email                 varchar(64) NOT NULL
);

# ---!Downs

DROP TABLE member;
