plugins {
    application
    kotlin("jvm") version "2.0.21"
}

group = "com.davidsalter.jsonconvert"
version = "1.0-SNAPSHOT"

application {
    mainClass = "com.davidsalter.jsonconvert.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.github.ajalt.clikt:clikt:5.0.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")
    archiveFileName.set("jsonconvert.jar")
    from(sourceSets.main.get().output)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    manifest {
        attributes("Main-Class" to "com.davidsalter.jsonconvert.MainKt")
    }
}