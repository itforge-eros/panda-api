# --- !Ups

CREATE TABLE permission (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           TEXT NOT NULL,
  code
);

# --- !Downs

DROP TABLE permission;
