package no.nav.dagpenger.oppslag.journalpost.id.api

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun AuthenticationConfig.jwt(name: String) {
    jwt(name) {
        verifier(AzureAd)
        validate { jwtClaims ->
            JWTPrincipal(jwtClaims.payload)
        }
    }
}
