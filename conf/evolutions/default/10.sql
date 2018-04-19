# --- !Ups

ALTER TABLE space ADD COLUMN category VARCHAR(64) NOT NULL DEFAULT 'CLASSROOM';

# --- !Downs

ALTER TABLE space DROP COLUMN category;
