plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
}

group = "fk.home"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}