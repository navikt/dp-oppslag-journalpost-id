package no.nav.dagpenger.oppslag.journalpost.id

import com.fasterxml.jackson.databind.JsonNode
import mu.KotlinLogging
import no.nav.helse.rapids_rivers.JsonMessage
import no.nav.helse.rapids_rivers.MessageContext
import no.nav.helse.rapids_rivers.RapidsConnection
import no.nav.helse.rapids_rivers.River
import java.util.UUID

private val logger = KotlinLogging.logger { }

class InnsendingFerdigstiltMottak(rapidsConnection: RapidsConnection, private val repository: Repository) :
    River.PacketListener {
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
        repository.lagre(søknadId, journalpostId)
        logger.info { "Lagret $søknadId -> $journalpostId" }
    }
}

private fun JsonNode.asUUID(): UUID = this.asText().let { UUID.fromString(it) }
