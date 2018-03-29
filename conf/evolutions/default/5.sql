# --- !Ups

CREATE TABLE reservation (
  id                    uuid PRIMARY KEY,
  date                  date NOT NULL,
  period                int4range NOT NULL
);

# ---!Downs

DROP TABLE reservation
