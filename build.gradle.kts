plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.charleslgn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

val webSocketVersion = "1.5.2"
val httpclientVersion = "4.5.14"
val klaxonVersion = "5.5"

val assertJVersion = "3.23.1"
val mockitoVersion = "3.10.0"

dependencies {
    implementation("org.java-websocket:Java-WebSocket:$webSocketVersion") { exclude("org.slf4j") }
    implementation("com.beust:klaxon:$klaxonVersion")
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

tasks.test {
    useJUnitPlatform()
}
