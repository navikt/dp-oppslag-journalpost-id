package no.nav.dagpenger.oppslag.journalpost.id

import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.mockk
import no.nav.dagpenger.oppslag.journalpost.id.api.journalpostApi
import no.nav.dagpenger.oppslag.journalpost.id.db.InMemoryJournalpostRepository
import no.nav.dagpenger.oppslag.journalpost.id.db.JournalpostRepository
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class JournalpostIdApiTest {
    val mockAzure = MockAzure()

    @Test
    fun `Skal kunne finne journalpost id fra søknad id`() {
        val journalpostId = "123"
        val søknadId = randomUUID()
        val repository =
            InMemoryJournalpostRepository().also {
                it.lagre(søknadId, journalpostId)
            }
        withOppgaveApi(journalpostRepository = repository) {
            client.get("v1/journalpost/$søknadId") {
                header(HttpHeaders.Authorization, "Bearer ${mockAzure.lagToken()}")
            }.let { response ->
                response.status shouldBe HttpStatusCode.OK
                response.bodyAsText() shouldBe journalpostId
            }
        }
    }

    @Test
    fun unauthorized() {
        withOppgaveApi {
            client.get("v1/journalpost/${randomUUID()}") {
                header(HttpHeaders.Authorization, "Bearer tull")
            }.let { response ->
                response.status shouldBe HttpStatusCode.Unauthorized
            }
        }
    }
}

private fun withOppgaveApi(
    journalpostRepository: JournalpostRepository = mockk<JournalpostRepository>(relaxed = true),
    test: suspend ApplicationTestBuilder.() -> Unit,
) {
    testApplication {
        application { journalpostApi(journalpostRepository) }
        test()
    }
}
