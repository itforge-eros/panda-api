# --- !Ups

CREATE TYPE request_status AS ENUM ('pending', 'completed', 'failed', 'cancelled');

CREATE TABLE request (
  id                    uuid PRIMARY KEY,
  body                  text,
  dates                 date[] NOT NULL,
  period                int4range NOT NULL,
  status                request_status NOT NULL,
  created_at            timestamp NOT NULL,
  space_id              uuid NOT NULL REFERENCES space (id) ON DELETE SET NULL,
  client_id             uuid NOT NULL REFERENCES member (id) ON DELETE RESTRICT
);

# --- !Downs

DROP TABLE request;

DROP TYPE request_status;
