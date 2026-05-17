plugins {
    kotlin("jvm") version "2.0.21"
}

group = "hoyolab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.7.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    implementation("io.github.bonigarcia:webdrivermanager:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.12")
}

tasks.test {
    useJUnitPlatform()

    systemProperty("browser", System.getProperty("browser", "chrome"))
    systemProperty("junit.jupiter.execution.timeout.default", "90 s")

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.register<Test>("testFirefox") {
    useJUnitPlatform()

    systemProperty("browser", "firefox")
    systemProperty("junit.jupiter.execution.timeout.default", "90 s")

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.register<Test>("testChrome") {
    useJUnitPlatform()

    systemProperty("browser", "chrome")
    systemProperty("junit.jupiter.execution.timeout.default", "90 s")

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

kotlin {
    jvmToolchain(17)
}