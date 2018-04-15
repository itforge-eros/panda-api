# --- !Ups

CREATE TABLE member_role (
  member_id             uuid NOT NULL REFERENCES member (id),
  role_id               uuid NOT NULL REFERENCES role (id),

  PRIMARY KEY (member_id, role_id)
);

# --- !Downs

DROP TABLE member_role;
