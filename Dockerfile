FROM maven:3.9.4-eclipse-temurin-17
COPY . .
RUN mvn clean package -Dmaven.test.skip

FROM arm64v8/openjdk:21-ea-17-jdk-slim-buster
COPY --from=0 /target/Plumbing-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "Plumbing-0.0.1-SNAPSHOT.jar"]

#FROM arm64v8/openjdk:21-ea-17-jdk-slim-buster
#COPY ./target/Plumbing-0.0.1-SNAPSHOT.jar .
#CMD ["java", "-jar", "Plumbing-0.0.1-SNAPSHOT.jar"]
