FROM openjdk:19
COPY ./target/Plumbing-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "Plumbing-0.0.1-SNAPSHOT.jar"]