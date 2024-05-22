package no.nav.dagpenger.oppslag.journalpost.id.api

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import no.nav.dagpenger.oppslag.journalpost.id.db.JournalpostRepository
import java.util.UUID

fun Application.journalpostApi(journalpostRepository: JournalpostRepository) {
    apiConfig()

    routing {
        authenticate("azureAd") {
            route("v1/journalpost/{søknadId}") {
                get {
                    call.respond(HttpStatusCode.OK, journalpostRepository.hent(call.søknadId()))
                }
            }
        }
    }
}

private fun ApplicationCall.søknadId() =
    parameters["søknadId"]?.let {
        UUID.fromString(it)
    } ?: throw IllegalArgumentException("Mangler søknadId")
