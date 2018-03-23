# --- !Ups

CREATE TABLE Space (
  id                    UUID,
  name                  VARCHAR(64) NOT NULL,
  description           TEXT,
  capacity              INT NOT NULL,
  required_approval     INT NOT NULL,
  is_reservable         BOOLEAN NOT NULL,
  CONSTRAINT            space_id PRIMARY KEY (id)
);

# ---!Downs

DROP TABLE Space
