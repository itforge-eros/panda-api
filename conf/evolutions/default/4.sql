# --- !Ups

CREATE TABLE request_review (
  request_id            uuid REFERENCES request (id),
  reviewer_id           uuid REFERENCES member (id),
  description           text,
  is_approval           boolean NOT NULL,
  created_at            timestamp NOT NULL,

  PRIMARY KEY (request_id, reviewer_id)
);

# ---!Downs

DROP TABLE request_review
