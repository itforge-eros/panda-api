# --- !Ups

CREATE TABLE member_role (
  member_id             uuid NOT NULL REFERENCES member (id),
  role_id               uuid NOT NULL REFERENCES role (id)
);

# --- !Downs

DROP TABLE member_role;
