import com.ewerk.gradle.plugins.tasks.QuerydslCompile

plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

group = "com.wanted"
version = "0.0.1-SNAPSHOT"
val queryDslVersion = "5.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
//    runtimeOnly ("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.vintage:junit-vintage-engine") {
        exclude("org.hamcrest", "hamcrest-core")
    }

    //test 롬복 사용
    testCompileOnly ("org.projectlombok:lombok")
    testAnnotationProcessor ("org.projectlombok:lombok")

    //Querydsl 추가
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor ("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jakarta")
    annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor ("jakarta.persistence:jakarta.persistence-api")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = true
    layered
}

tasks.bootBuildImage {
    imageName = "wanted/preonboarding-backend"
}

val querydslDir = layout.buildDirectory.dir("generated/querydsl").get().toString()

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}

sourceSets.getByName("main") {
    java.srcDir(querydslDir)
}

configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}

tasks.withType<QuerydslCompile> {
    options.annotationProcessorPath = configurations.querydsl.get()
}