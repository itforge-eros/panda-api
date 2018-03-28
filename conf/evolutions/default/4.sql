# --- !Ups

CREATE TABLE approval (
  request_id            UUID REFERENCES request (id),
  approver_id           UUID REFERENCES member (id),
  created_at            TIMESTAMP NOT NULL,

  PRIMARY KEY (request_id, approver_id)
);

# ---!Downs

DROP TABLE approval
