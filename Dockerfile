FROM openjdk:17-jdk-slim AS BUILD_STAGE
WORKDIR /app
COPY . .
RUN ./gradlew build --exclude-task test

FROM openjdk:17-jdk-slim
COPY --from=BUILD_STAGE app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
