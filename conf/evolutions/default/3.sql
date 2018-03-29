# --- !Ups

CREATE TABLE request (
  id                    uuid PRIMARY KEY,
  proposal              text,
  date                  date[] NOT NULL,
  interval              int4range NOT NULL,
  created_at            timestamp NOT NULL,
  space_id              uuid NOT NULL REFERENCES space (id),
  client_id             uuid NOT NULL REFERENCES member (id)
);

# ---!Downs

DROP TABLE request
