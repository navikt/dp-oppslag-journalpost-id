package no.nav.dagpenger.oppslag.journalpost.id

import java.util.UUID

interface Repository {
    fun lagre(
        søknadId: UUID,
        journalpostId: String,
    )

    fun hent(søknadId: UUID): String

    class JournalpostIkkeFunnet(msg: String) : RuntimeException(msg)
}

class InmemoryRepository : Repository {
    private val storage = mutableMapOf<UUID, String>()

    override fun lagre(
        søknadId: UUID,
        journalpostId: String,
    ) {
        storage[søknadId] = journalpostId
    }

    override fun hent(søknadId: UUID): String =
        storage[søknadId]
            ?: throw Repository.JournalpostIkkeFunnet("Fant ikke journalpost for søknadId $søknadId")
}
