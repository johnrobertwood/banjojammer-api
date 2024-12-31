# Use an official JDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project's jar file into the container at /app
COPY target/banjojammer-0.0.3.jar /app/banjojammer.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "banjojammer.jar"]