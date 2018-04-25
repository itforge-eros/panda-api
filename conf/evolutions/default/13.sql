# --- !Ups

ALTER TABLE space DROP COLUMN category;

# --- !Downs

ALTER TABLE space ADD COLUMN category varchar(64) NOT NULL DEFAULT 'CLASSROOM';
