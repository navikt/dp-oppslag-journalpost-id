CREATE TABLE IF NOT EXISTS soknad_id_journalpost_id_mapping_v1
(
    soknad_id            UUID PRIMARY KEY,
    journalpost_id       TEXT                        NOT NULL,
    registrert_tidspunkt TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);