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
    js {
        useEsModules()
        browser ()
        binaries.executable()
        generateTypeScriptDefinitions()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jsMain
    }
}
