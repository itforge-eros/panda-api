# --- !Ups

CREATE TYPE review_event AS ENUM ('approve', 'reject', 'comment');

CREATE TABLE review (
  id                    uuid PRIMARY KEY,
  body                  text,
  event                 review_event NOT NULL,
  created_at            timestamp NOT NULL,
  request_id            uuid NOT NULL REFERENCES request (id),
  reviewer_id           uuid NOT NULL REFERENCES member (id)
);

# --- !Downs

DROP TABLE review;

DROP TYPE review_event;
