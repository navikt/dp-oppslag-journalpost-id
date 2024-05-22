package no.nav.dagpenger.oppslag.journalpost.id

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import no.nav.helse.rapids_rivers.testsupport.TestRapid
import org.junit.jupiter.api.Test
import java.util.UUID

class InnsendingFerdigstilMottakTest {
    private val testRapid = TestRapid()

    @Test
    fun `Skal ta tak i riktige pakker`() {
        val repository =
            mockk<Repository>().also {
                every { it.lagre(any(), any()) } just runs
            }

        InnsendingFerdigstilMottak(testRapid, repository)

        //language=JSON
        testRapid.sendTestMessage(
            """ 
            {
              "journalpostId": "662317896",
              "fagsakId": "14916100",
              "@event_name": "innsending_ferdigstilt",
              "type": "NySøknad",
              "søknadsData": {
                  "søknad_uuid": "f0509e9a-f913-45cb-9aa7-ed7bafcb9e93"
               }
            }
            """.trimIndent(),
        )

        verify(exactly = 1) { repository.lagre(UUID.fromString("f0509e9a-f913-45cb-9aa7-ed7bafcb9e93"), "662317896") }
    }
}
