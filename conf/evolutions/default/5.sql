# --- !Ups

CREATE EXTENSION btree_gist;

CREATE TABLE reservation (
  id                    uuid PRIMARY KEY,
  date                  date NOT NULL,
  period                int4range NOT NULL,
  is_attended           boolean NOT NULL,
  space_id              uuid REFERENCES space (id),

  EXCLUDE USING gist (space_id WITH =, period WITH &&)
);

# ---!Downs

DROP TABLE reservation
