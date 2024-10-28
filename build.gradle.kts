import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	val kotlinVersion = "2.0.21"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
	id("org.jmailen.kotlinter") version "4.4.1"
}

group = "com.github.rbleuse"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
	mavenCentral()
}

dependencies {
	val jjwtVersion = "0.12.6"
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("net.datafaker:datafaker:2.4.1")
	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	implementation("org.ocpsoft.prettytime:prettytime:5.0.9.Final")

	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
	useJUnitPlatform()
}
