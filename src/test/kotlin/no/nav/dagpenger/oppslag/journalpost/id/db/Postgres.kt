package no.nav.dagpenger.oppslag.journalpost.id.db

import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.internal.configuration.ConfigUtils
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

    fun setup() {
        System.setProperty(ConfigUtils.CLEAN_DISABLED, "false")
        System.setProperty(PostgresDataSourceBuilder.DB_URL_KEY, instance.jdbcUrl)
        System.setProperty(PostgresDataSourceBuilder.DB_USERNAME_KEY, instance.username)
        System.setProperty(PostgresDataSourceBuilder.DB_PASSWORD_KEY, instance.password)
    }

    fun tearDown() {
        System.clearProperty(PostgresDataSourceBuilder.DB_URL_KEY)
        System.clearProperty(PostgresDataSourceBuilder.DB_USERNAME_KEY)
        System.clearProperty(PostgresDataSourceBuilder.DB_PASSWORD_KEY)
        System.clearProperty(ConfigUtils.CLEAN_DISABLED)
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
