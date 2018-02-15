# Builder container
FROM openjdk:8-jdk-alpine AS builder

WORKDIR /app
ADD gradlew build.gradle /app/
ADD gradle /app/gradle
ADD src /app/src

USER root
RUN ./gradlew build -w --no-daemon --console plain

# Production container
# Note: Beam/Dataflow does not run well on alpine
FROM openjdk:8-jdk-slim
COPY --from=builder /app/build/libs/beam-scheduling-kubernetes-*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]