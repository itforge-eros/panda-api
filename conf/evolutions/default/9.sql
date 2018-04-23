# --- !Ups

CREATE TABLE member_role (
  member_id             uuid NOT NULL REFERENCES member (id) ON DELETE RESTRICT,
  role_id               uuid NOT NULL REFERENCES role (id) ON DELETE CASCADE,
  created_at            timestamp NOT NULL,

  PRIMARY KEY (member_id, role_id)
);

# --- !Downs

DROP TABLE member_role;
