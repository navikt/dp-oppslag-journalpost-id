## dp-oppslag-journalpost-id

## Formålet med appen
Appen er en del av saksbehandlerflaten til dagpenger og brukes til å hente søknadens journalpost-id 
ved å lytte på ```innsending_ferdigstilt``` hendelser som publiseres fra [dp-mottak](https://github.com/navikt/dp-mottak)

JournalpostIdene blir lagret i en database og kan hentes ut via et REST-API. 
I dag blir APIet brukt av [saksbehandlerflaten](https://github.com/navikt/dp-saksbehandling)

### Begrensninger
Appen er en midlertidig fiks for å vise en dagpengesøkers innsendte søknad i saksbehandlerflaten.
Denne plukker kun opp journalpost-id for søknaden, og ikke øvrige innsendinger og ettersendelser.
Saksbehandler må derfor finne øvrige dokumenter i Gosys manuelt, inntil en bedre løsning finnes 
for alle journalførte dokumenter i dagpengesaken.

## Komme i gang
Gradle brukes som byggverktøy og er bundlet inn. Spotless brukes til linting.

`./gradlew spotlessApply build`

## Henvendelser 
Spørsmål kan stilles som issues på github.

### For NAV-ansatte
Interne henvendelser kan sendes via Slack i kanalen #team-dagpenger-saksbehandlerflate
