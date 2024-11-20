package no.nav.dagpenger.oppslag.journalpost.id.api

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.apiConfig() {
    install(Authentication) {
        jwt("azureAd") {
            verifier(AzureAd)
            validate { jwtClaims ->
                JWTPrincipal(jwtClaims.payload)
            }
        }
    }
}
