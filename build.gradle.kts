plugins {
    kotlin("jvm") version "2.0.21"
}

group = "hoyolab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.27.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.16")
}

fun Test.configureUiTest(browserName: String) {
    useJUnitPlatform {
        excludeTags("manual")
    }

    systemProperty("browser", browserName)
    systemProperty("junit.jupiter.execution.timeout.default", "120 s")
    systemProperty("junit.jupiter.execution.parallel.enabled", "false")

    val testFilter = project.findProperty("testFilter")?.toString()
    if (!testFilter.isNullOrBlank()) {
        filter {
            includeTestsMatching(testFilter)
        }
    }

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    maxParallelForks = 1

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.test {
    configureUiTest(System.getProperty("browser", "chrome"))
}

tasks.register<Test>("testChrome") {
    group = "verification"
    description = "Runs tests in Chrome"

    configureUiTest("chrome")
}

tasks.register<Test>("testFirefox") {
    group = "verification"
    description = "Runs tests in Firefox"

    configureUiTest("firefox")
}

tasks.register<Exec>("testBothBrowsers") {
    group = "verification"
    description = "Runs selected tests in Chrome and Firefox at the same time"

    val testFilter = project.findProperty("testFilter")?.toString()

    val chromeCommand =
        if (testFilter.isNullOrBlank()) {
            "gradlew.bat testChrome"
        } else {
            "gradlew.bat testChrome -PtestFilter=$testFilter"
        }

    val firefoxCommand =
        if (testFilter.isNullOrBlank()) {
            "gradlew.bat testFirefox"
        } else {
            "gradlew.bat testFirefox -PtestFilter=$testFilter"
        }

    commandLine(
        "powershell",
        "-Command",
        "Start-Process cmd -ArgumentList '/k $chromeCommand'; Start-Process cmd -ArgumentList '/k $firefoxCommand'"
    )
}

kotlin {
    jvmToolchain(17)
}