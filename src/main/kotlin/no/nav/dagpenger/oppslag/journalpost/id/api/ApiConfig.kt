package no.nav.dagpenger.oppslag.journalpost.id.api

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.document
import mu.KotlinLogging

private val sikkerLogger = KotlinLogging.logger("tjenestekall")

fun Application.apiConfig() {
    install(CallLogging) {
        disableDefaultColors()
        filter { call ->
            !setOf(
                "isalive",
                "isready",
                "metrics",
            ).contains(call.request.document())
        }
    }

    install(Authentication) {
        jwt("azureAd")
    }
}
