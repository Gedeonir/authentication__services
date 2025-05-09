# Step 1: Build the application with JDK 21 and Maven
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
# Install Maven
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image with JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
