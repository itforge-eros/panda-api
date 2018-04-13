# --- !Ups

CREATE TABLE "group" (
  id                    uuid PRIMARY KEY,
  name                  varchar(64) NOT NULL,
  description           text
);

ALTER TABLE space ADD group_id uuid REFERENCES "group" (id);

# --- !Downs

ALTER TABLE space DROP group_id;

DROP TABLE "group";
