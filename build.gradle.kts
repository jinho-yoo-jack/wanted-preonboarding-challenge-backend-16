plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.cloud.tools.jib") version "3.4.0"
}

group = "com.wanted"
version = "0.0.1-SNAPSHOT"

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
    compileOnly("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
	runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.junit.vintage:junit-vintage-engine") {
        exclude("org.hamcrest", "hamcrest-core")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}


tasks.bootJar {
    enabled = true
    layered
}

tasks.bootBuildImage {
    imageName = "wanted/preonboarding-backend"
}
//	이미지 레이어링으로 효율적인 빌드
jib {
    from.image = "openjdk:17-jdk-slim"
	to.image = "wanted/preonboarding-backend"
	container.ports = listOf("8016")
}
tasks.register("dockerComposeUp", Exec::class) {
	commandLine("docker-compose", "up", "-d")
}
tasks.register("dockerComposeDown", Exec::class) {
	commandLine("docker-compose", "down")
}
//	빌드와 실행을 분리
tasks.register("start") {
    dependsOn("jibDockerBuild")
    finalizedBy("dockerComposeUp")
}
tasks.register("stop") {
	finalizedBy("dockerComposeDown")
}
