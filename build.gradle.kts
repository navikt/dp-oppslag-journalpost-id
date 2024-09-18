import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("common")
    application
    alias(libs.plugins.shadow.jar)
}

dependencies {

    implementation(libs.kotlin.logging)
    implementation(libs.rapids.and.rivers)
    implementation(libs.konfig)
    implementation(libs.bundles.postgres)
    implementation(libs.bundles.ktor.server)

    testImplementation(libs.mockk)
    testImplementation(libs.kotest.assertions.core)
    testImplementation("io.ktor:ktor-server-test-host-jvm:${libs.versions.ktor.get()}")
    testImplementation(libs.bundles.postgres.test)
    testImplementation(libs.mock.oauth2.server)
}

application {
    mainClass.set("no.nav.dagpenger.oppslag.journalpost.id.AppKt")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}
