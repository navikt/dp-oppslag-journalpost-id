package no.nav.dagpenger.oppslag.journalpost.id

import java.util.UUID

interface Repository {
    fun lagre(
        journalpostId: String,
        søknadId: UUID,
    )
}
