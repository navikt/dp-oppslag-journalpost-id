package no.nav.dagpenger.oppslag.journalpost.id.db

import io.kotest.matchers.shouldBe
import no.nav.dagpenger.oppslag.journalpost.id.db.Postgres.withMigratedDb
import no.nav.dagpenger.oppslag.journalpost.id.db.PostgresDataSourceBuilder.dataSource
import org.junit.jupiter.api.Test
import java.util.UUID

class JournalpostPostgresRepositoryTest {
    @Test
    fun `Skal kunne lagre og hente journalpostId basert på søknadId`() =
        withMigratedDb {
            val journalpostRepository = JournalpostPostgresRepository(dataSource)
            val søknadId = UUID.randomUUID()
            val journalpostId = "123"
            journalpostRepository.lagre(søknadId, journalpostId)
            journalpostRepository.hent(søknadId) shouldBe journalpostId
        }
}
