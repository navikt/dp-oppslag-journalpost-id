package no.nav.dagpenger.oppslag.journalpost.id

import mu.KotlinLogging
import no.nav.helse.rapids_rivers.RapidApplication
import no.nav.helse.rapids_rivers.RapidsConnection

internal class ApplicationBuilder(config: Map<String, String>) : RapidsConnection.StatusListener {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    private val rapidsConnection: RapidsConnection =
        RapidApplication.Builder(RapidApplication.RapidApplicationConfig.fromEnv(config))
            .withKtorModule {
            }.build()

    init {
        rapidsConnection.register(this)
    }

    fun start() {
//        rapidsConnection.start()
    }

    fun stop() {
//        rapidsConnection.stop()
    }

    override fun onStartup(rapidsConnection: RapidsConnection) {
        logger.info { "Starter opp dp-oppslag-journalpost-id" }
    }
}
