import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform") version "2.1.20"
    `maven-publish`
}

group = "no.howie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        commonMain
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api")
                implementation("org.junit.jupiter:junit-jupiter-params")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine")
            }
        }
    }
}
