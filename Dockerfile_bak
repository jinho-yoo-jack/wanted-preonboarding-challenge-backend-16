FROM adoptopenjdk/openjdk11 AS builder
#gradlew 복사
COPY gradlew .
#gradle 복사
COPY gradle gradle
#build.gradle 복사
COPY build.gradle.kts .
#settings.gradle 복사
COPY settings.gradle.kts .
# Copy Web Application Source
COPY src src
RUN chmod +x ./gradlew	#gradlew 실행 권한 부여
RUN ./gradlew bootJar	#gradlew를 통해 실행 가능한 jar파일 생성

#베이스 이미지 생성
FROM adoptopenjdk/openjdk11
#build이미지에서 build/libs/*.jar 파일을 app.jar로 복사
COPY --from=builder build/libs/*.jar wanted_ticket.jar

#jar 파일 실행
ENTRYPOINT ["java", "-jar", "wanted_ticket.jar"]
#볼륨 지정
VOLUME /tmp