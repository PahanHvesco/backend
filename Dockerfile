FROM openjdk:17
COPY . /app
WORKDIR /app
RUN ./mvnw package
CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]