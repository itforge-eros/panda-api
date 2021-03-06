# --- !Ups

CREATE TABLE member (
  id                    uuid PRIMARY KEY,
  username              varchar(64) UNIQUE NOT NULL,
  first_name            varchar(64) NOT NULL,
  last_name             varchar(64) NOT NULL,
  email                 varchar(64) NOT NULL,
  created_at            timestamp NOT NULL,
  updated_at            timestamp NOT NULL
);

# --- !Downs

DROP TABLE member;
