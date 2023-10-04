# Use official Maven image as the build environment
FROM maven:3.8-jdk-11 as builder

# Set the working directory in Docker
WORKDIR /app

# Copy the pom.xml and src directory (Maven's default source code directory)
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Use OpenJDK to run the compiled application
FROM openjdk:11-jre

# Copy the packaged application from the builder image
COPY --from=builder /app/target/*.jar /app.jar

# Command to run the application
CMD ["java", "-jar", "/app.jar"]
