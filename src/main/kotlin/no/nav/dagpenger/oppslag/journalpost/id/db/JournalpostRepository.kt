package no.nav.dagpenger.oppslag.journalpost.id.db

import java.util.UUID

interface JournalpostRepository {
    fun lagre(
        søknadId: UUID,
        journalpostId: String,
    )

    fun hent(søknadId: UUID): String

    class JournalpostIkkeFunnet(msg: String) : RuntimeException(msg)
}
