package no.nav.dagpenger.oppslag.journalpost.id

import com.fasterxml.jackson.databind.JsonNode
import com.github.navikt.tbd_libs.rapids_and_rivers.JsonMessage
import com.github.navikt.tbd_libs.rapids_and_rivers.River
import com.github.navikt.tbd_libs.rapids_and_rivers_api.MessageContext
import com.github.navikt.tbd_libs.rapids_and_rivers_api.RapidsConnection
import mu.KotlinLogging
import no.nav.dagpenger.oppslag.journalpost.id.db.JournalpostRepository
import java.util.UUID

private val logger = KotlinLogging.logger { }

class InnsendingFerdigstiltMottak(
    rapidsConnection: RapidsConnection,
    private val journalpostRepository: JournalpostRepository,
) : River.PacketListener {
    init {
        River(rapidsConnection).apply {
            validate { it.demandValue("@event_name", "innsending_ferdigstilt") }
            validate { it.requireValue("type", value = "NySøknad") }
            validate { it.requireKey("fagsakId") }
            validate { it.requireKey("journalpostId") }
            validate { it.requireKey("søknadsData.søknad_uuid") }
        }.register(this)
    }

    override fun onPacket(
        packet: JsonMessage,
        context: MessageContext,
    ) {
        val journalpostId = packet["journalpostId"].asText()
        val søknadId = packet["søknadsData.søknad_uuid"].asUUID()
        journalpostRepository.lagre(søknadId, journalpostId)
        logger.info { "Lagret $søknadId -> $journalpostId" }
    }
}

private fun JsonNode.asUUID(): UUID = this.asText().let { UUID.fromString(it) }
