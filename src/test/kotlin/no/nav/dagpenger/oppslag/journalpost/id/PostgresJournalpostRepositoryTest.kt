package no.nav.dagpenger.oppslag.journalpost.id

import io.kotest.matchers.shouldBe
import no.nav.dagpenger.oppslag.journalpost.id.Postgres.withMigratedDb
import org.junit.jupiter.api.Test
import java.util.UUID

class PostgresJournalpostRepositoryTest {
    @Test
    fun `Skal kunne lagre og hente journalpostId basert på søknadId`() =
        withMigratedDb { ds ->
            val journalpostRepository = PostgresJournalpostRepository(dataSource = ds)
            val søknadId = UUID.randomUUID()
            val journalpostId = "123"
            journalpostRepository.lagre(søknadId, journalpostId)
            journalpostRepository.hent(søknadId) shouldBe journalpostId
        }
}
