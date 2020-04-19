FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=drop/target/AuthPOC-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} api-service.jar

# Run the jar file
RUN sh -c 'touch /api-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api-service.jar"]
