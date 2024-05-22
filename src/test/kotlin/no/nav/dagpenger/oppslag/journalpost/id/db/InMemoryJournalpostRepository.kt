package no.nav.dagpenger.oppslag.journalpost.id.db

import java.util.UUID

class InMemoryJournalpostRepository : JournalpostRepository {
    private val storage = mutableMapOf<UUID, String>()

    override fun lagre(
        søknadId: UUID,
        journalpostId: String,
    ) {
        storage[søknadId] = journalpostId
    }

    override fun hent(søknadId: UUID): String =
        storage[søknadId]
            ?: throw JournalpostRepository.JournalpostIkkeFunnet("Fant ikke journalpost for søknadId $søknadId")
}
