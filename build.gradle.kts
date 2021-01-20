import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.github.johnrengelman.processes") version "0.5.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.3.0"
    id("org.hidetake.swagger.generator") version "2.18.2"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "net.be-geek.petclinic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val springdocOpenApiVersion by extra { "1.4.1" }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:${springdocOpenApiVersion}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${springdocOpenApiVersion}")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    swaggerUI("org.webjars:swagger-ui:3.10.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.register("buildFrontEnd") {
    doFirst {
        project.delete {
            delete("src/main/resources/static")
        }
        val keep = File("src/main/resources/static/.keep")
        keep.parentFile.mkdirs()
        keep.createNewFile()
    }
    doLast {
        project.exec {
            workingDir("src/front-end")
            commandLine("npm", "install")
        }
        project.exec {
            workingDir("src/front-end")
            commandLine("npm", "run", "build")
        }
        project.copy {
            from("src/front-end/build")
            into("src/main/resources/static")
        }
    }
}

swaggerSources {
    register("petstore") {
        setInputFile(file("build/openapi.json"))
    }
}
