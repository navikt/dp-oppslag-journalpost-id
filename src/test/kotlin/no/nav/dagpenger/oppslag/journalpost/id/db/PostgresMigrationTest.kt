package no.nav.dagpenger.oppslag.journalpost.id.db

import io.kotest.matchers.shouldBe
import no.nav.dagpenger.oppslag.journalpost.id.db.Postgres.withCleanDb
import no.nav.dagpenger.oppslag.journalpost.id.db.PostgresDataSourceBuilder.runMigration
import org.junit.jupiter.api.Test

class PostgresMigrationTest {
    @Test
    fun `Migration scripts are applied successfully`() {
        withCleanDb {
            val migrations = runMigration()
            migrations shouldBe 1
        }
    }
}
