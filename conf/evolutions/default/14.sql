# --- !Ups

ALTER TABLE request ADD COLUMN materials varchar(64)[] NOT NULL DEFAULT '{}';

# --- !Downs

ALTER TABLE request DROP COLUMN materials;
