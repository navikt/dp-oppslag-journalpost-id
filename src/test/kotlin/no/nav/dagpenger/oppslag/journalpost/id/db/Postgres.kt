package no.nav.dagpenger.oppslag.journalpost.id.db

import com.zaxxer.hikari.HikariDataSource
import org.testcontainers.containers.PostgreSQLContainer

internal object Postgres {
    val instance by lazy {
        PostgreSQLContainer<Nothing>("postgres:15.5").apply {
            start()
        }
    }

    fun withMigratedDb(block: () -> Unit) {
        withCleanDb {
            PostgresDataSourceBuilder.runMigration()
            block()
        }
    }

    fun withMigratedDb(): HikariDataSource {
        setup()
        PostgresDataSourceBuilder.runMigration()
        return PostgresDataSourceBuilder.dataSource
    }

    private fun setup() {
        System.setProperty(
            PostgresDataSourceBuilder.DB_JDBC_URL_KEY,
            instance.jdbcUrl + "&user=${instance.username}&password=${instance.password}",
        )
    }

    private fun tearDown() {
        System.clearProperty(PostgresDataSourceBuilder.DB_JDBC_URL_KEY)
    }

    fun withCleanDb(block: () -> Unit) {
        setup()
        PostgresDataSourceBuilder.clean().run {
            block()
        }.also {
            tearDown()
        }
    }

    fun withCleanDb(
        target: String,
        setup: () -> Unit,
        test: () -> Unit,
    ) {
        Postgres.setup()
        PostgresDataSourceBuilder.clean().run {
            PostgresDataSourceBuilder.runMigrationTo(target)
            setup()
            PostgresDataSourceBuilder.runMigration()
            test()
        }.also {
            tearDown()
        }
    }
}
