# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .

# Download the dependencies before copying the full source code (to leverage Docker cache)
RUN mvn dependency:go-offline

# Copy the rest of the source code to the container
COPY src ./src

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Create the final runtime image
FROM openjdk:17.0.1-jdk-slim

# Set the working directory inside the final container
WORKDIR /app

# Copy the built artifact (from stage 1) into the runtime image
COPY --from=build /app/target/EzyRide-0.0.1-SNAPSHOT.jar EzyRide.jar

# Set the entrypoint command to run the JAR
ENTRYPOINT ["java", "-jar", "EzyRide.jar"]