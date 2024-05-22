import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("common")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
}

application {
    mainClass.set("no.nav.dagpenger.oppslag.journalpost.id.AppKt")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}
