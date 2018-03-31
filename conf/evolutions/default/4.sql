# --- !Ups

CREATE TABLE review (
  request_id            uuid NOT NULL REFERENCES request (id),
  reviewer_id           uuid NOT NULL REFERENCES member (id),
  description           text,
  is_approval           boolean NOT NULL,
  created_at            timestamp NOT NULL,

  PRIMARY KEY (request_id, reviewer_id)
);

# ---!Downs

DROP TABLE review
