package no.nav.dagpenger.oppslag.journalpost.id

import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class JournalpostidApiTest {
    @Test
    fun `Skal kunne finne journalpost id fra søknad id`() {
        val journalpostId = "123"
        val søknadId = randomUUID()
        val repository =
            InmemoryRepository().also {
                it.lagre(søknadId, journalpostId)
            }
        withOppgaveApi(repository = repository) {
            client.get("v1/journalpost/$søknadId").let { response ->
                response.status shouldBe HttpStatusCode.OK
                response.bodyAsText() shouldBe journalpostId
            }
        }
    }
}

private fun withOppgaveApi(
    repository: Repository = mockk<Repository>(relaxed = true),
    test: suspend ApplicationTestBuilder.() -> Unit,
) {
    testApplication {
        application { journalpostApi(repository) }
        test()
    }
}
