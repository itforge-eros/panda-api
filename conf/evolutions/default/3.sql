# --- !Ups

CREATE TABLE request (
  id                    UUID PRIMARY KEY,
  purposal              TEXT,
  created_at            TIMESTAMP NOT NULL,
  space_id              UUID NOT NULL REFERENCES space (id),
  client_id             UUID NOT NULL REFERENCES member (id)
);

# ---!Downs

DROP TABLE request
