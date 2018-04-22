# --- !Ups

CREATE TABLE problem (
  id                    uuid PRIMARY KEY,
  title                 varchar(64) NOT NULL,
  body                  text NOT NULL,
  is_read               boolean NOT NULL,
  space_id              uuid NOT NULL REFERENCES space (id) ON DELETE CASCADE
);

# --- !Downs

DROP TABLE problem;
