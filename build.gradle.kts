import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.muriloghignatti"
version = "1.0.0"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    implementation(project(":SafeUtils"))
    implementation(project(":SafeKit"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}