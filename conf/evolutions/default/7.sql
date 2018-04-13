# --- !Ups

CREATE TYPE request_status AS ENUM ('pending', 'approved', 'rejected', 'cancelled');
ALTER TABLE request ADD status request_status NOT NULL DEFAULT 'pending';

# --- !Downs

ALTER TABLE request drop status;
DROP TYPE request_status;
