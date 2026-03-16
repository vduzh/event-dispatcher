plugins {
    `java-library`
    `maven-publish`
}

group = "by.vduzh.event.dispatcher"
version = "1.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
    testRuntimeOnly(libs.byte.buddy)
    testRuntimeOnly(libs.byte.buddy.agent)
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

testing.suites.named<JvmTestSuite>("test") {
    useJUnitJupiter()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
    repositories {
        maven {
            name = "nexus"
            val path = if (version.toString().endsWith("SNAPSHOT")) "maven-snapshots" else "maven-releases"
            url = uri("http://localhost:8081/repository/$path")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_USERNAME") ?: "admin"
                password = System.getenv("NEXUS_PASSWORD") ?: "admin123"
            }
        }
    }
}
