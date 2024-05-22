package no.nav.dagpenger.oppslag.journalpost.id

import io.kotest.matchers.shouldBe
import no.nav.dagpenger.oppslag.journalpost.id.Postgres.withMigratedDb
import no.nav.dagpenger.oppslag.journalpost.id.PostgresDataSourceBuilder.dataSource
import org.junit.jupiter.api.Test
import java.util.UUID

class PostgresJournalpostRepositoryTest {
    @Test
    fun `Skal kunne lagre og hente journalpostId basert på søknadId`() =
        withMigratedDb {
            val journalpostRepository = PostgresJournalpostRepository(dataSource)
            val søknadId = UUID.randomUUID()
            val journalpostId = "123"
            journalpostRepository.lagre(søknadId, journalpostId)
            journalpostRepository.hent(søknadId) shouldBe journalpostId
        }
}
