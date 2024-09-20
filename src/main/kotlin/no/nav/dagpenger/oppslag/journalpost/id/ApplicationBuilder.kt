package no.nav.dagpenger.oppslag.journalpost.id

import com.github.navikt.tbd_libs.rapids_and_rivers_api.RapidsConnection
import mu.KotlinLogging
import no.nav.dagpenger.oppslag.journalpost.id.api.journalpostApi
import no.nav.dagpenger.oppslag.journalpost.id.db.JournalpostPostgresRepository
import no.nav.dagpenger.oppslag.journalpost.id.db.PostgresDataSourceBuilder.dataSource
import no.nav.dagpenger.oppslag.journalpost.id.db.PostgresDataSourceBuilder.runMigration
import no.nav.helse.rapids_rivers.RapidApplication

internal class ApplicationBuilder(config: Map<String, String>) : RapidsConnection.StatusListener {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    val repository = JournalpostPostgresRepository(dataSource)

    private val rapidsConnection: RapidsConnection =
        RapidApplication.create(config) { engine, _ ->
            engine.application.journalpostApi(repository)
        }.also { rapidsConnection ->
            InnsendingFerdigstiltMottak(rapidsConnection, repository)
        }

    init {
        rapidsConnection.register(this)
    }

    fun start() {
        rapidsConnection.start()
    }

    fun stop() {
        rapidsConnection.stop()
    }

    override fun onStartup(rapidsConnection: RapidsConnection) {
        runMigration()
        logger.info { "Starter opp dp-oppslag-journalpost-id" }
    }

    override fun onShutdown(rapidsConnection: RapidsConnection) {
        logger.info { "Skrur av applikasjonen" }
        stop()
    }
}
