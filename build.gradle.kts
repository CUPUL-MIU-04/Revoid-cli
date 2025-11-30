import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    application
    `maven-publish`
    signing
}

group = "com.revoid"

val githubUser = project.findProperty("gpr.user") as String? ?: System.getenv("SECRET_USER")
val githubToken = project.findProperty("gpr.key") as String? ?: System.getenv("SECRET_TOKEN")

application {
    mainClass = "com.revoid.cli.command.MainCommandKt"
}

repositories {
    mavenCentral()
    google()
    
    // Si tienes tus propias bibliotecas ReVoid en GitHub Packages
    maven {
        url = uri("https://maven.pkg.github.com/CUPUL-MIU-04/revoid-patcher")
        credentials {
            username = githubUser ?: "dummy"
            password = githubToken ?: "dummy"
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/CUPUL-MIU-04/revoid-library")
        credentials {
            username = githubUser ?: "dummy"
            password = githubToken ?: "dummy"
        }
    }
}

dependencies {
    // TUS propias implementaciones de ReVoid
    implementation("com.revoid:patcher:1.0.0") // Ajusta la versión
    implementation("com.revoid:library:1.0.0")  // Ajusta la versión
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.picocli)
    implementation(libs.gson)

    testImplementation(libs.kotlin.test)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "SKIPPED", "FAILED")
        }
    }

    processResources {
        expand("projectVersion" to project.version)
    }

    shadowJar {
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*"))
            exclude(dependency("org.bouncycastle:.*"))
            exclude(dependency("com.revoid:.*")) // Cambiado de app.revanced a com.revoid
        }
    }

    publish {
        dependsOn(shadowJar)
    }
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("revoid-cli-publication") {
            from(components["java"])
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["revoid-cli-publication"])
}
