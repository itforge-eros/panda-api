# --- !Ups

CREATE TABLE permission (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           TEXT NOT NULL
);

# --- !Downs

DROP TABLE permission;
