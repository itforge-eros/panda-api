# --- !Ups

CREATE TABLE request (
  id                    uuid PRIMARY KEY,
  proposal              text,
  dates                 date[] NOT NULL,
  period                int4range NOT NULL,
  created_at            timestamp NOT NULL,
  space_id              uuid NOT NULL REFERENCES space (id),
  client_id             uuid NOT NULL REFERENCES member (id)
);

# ---!Downs

DROP TABLE request;
