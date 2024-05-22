package no.nav.dagpenger.oppslag.journalpost.id

import kotliquery.queryOf
import kotliquery.sessionOf
import java.util.UUID
import javax.sql.DataSource

class PostgresJournalpostRepository(private val dataSource: DataSource) : JournalpostRepository {
    override fun lagre(
        søknadId: UUID,
        journalpostId: String,
    ) {
        sessionOf(dataSource).use { session ->
            session.run(
                queryOf(
                    //language=PostgreSQL
                    statement =
                        """
                        INSERT INTO soknad_id_journalpost_id_mapping_v1
                            (soknad_id, journalpost_id) 
                        VALUES
                            (:soknad_id, :journalpost_id) 
                        ON CONFLICT (soknad_id) DO NOTHING
                        """.trimIndent(),
                    paramMap =
                        mapOf(
                            "soknad_id" to søknadId,
                            "journalpost_id" to journalpostId,
                        ),
                ).asUpdate,
            )
        }
    }

    override fun hent(søknadId: UUID): String {
        sessionOf(dataSource).use { session ->
            return session.run(
                queryOf(
                    //language=PostgreSQL
                    statement =
                        """
                        SELECT journalpost_id 
                        FROM soknad_id_journalpost_id_mapping_v1
                        WHERE soknad_id = :soknad_id 
                        """.trimIndent(),
                    paramMap = mapOf("soknad_id" to søknadId),
                ).map { row ->
                    row.string("journalpost_id")
                }.asSingle,
            ) ?: throw JournalpostRepository.JournalpostIkkeFunnet("Fant ikke journapostId for søknadId: $søknadId")
        }
    }
}
