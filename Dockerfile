#FROM openjdk:17
FROM arm64v8/openjdk:21-ea-17-jdk-slim-buster
COPY ./target/Plumbing-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "Plumbing-0.0.1-SNAPSHOT.jar"]