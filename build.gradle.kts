plugins {
    id("java")
    `java-library`
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.nopermission.joineventshq"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    implementation("dev.dejvokep:boosted-yaml:1.3")

    //PAPI
    compileOnly("me.clip:placeholderapi:2.11.1")

    //Command Framework
    implementation("me.mattstudios.utils:matt-framework:1.4")

    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")
    compileOnly("net.kyori:adventure-api:4.12.0")

    implementation("dev.triumphteam:triumph-gui:3.1.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

kotlin {
    jvmToolchain(17)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    shadowJar {
        relocate("me.mattstudios.mf", "com.nopermission.libs.f")
        archiveFileName.set("JoinEventsHQ-v${project.version}.jar")
    }

    build {
        dependsOn(shadowJar)
    }

}