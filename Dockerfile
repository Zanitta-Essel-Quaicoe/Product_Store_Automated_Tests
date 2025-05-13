# Use OpenJDK 21 base image
FROM eclipse-temurin:21-jdk

# Install Maven and other utilities
RUN apt-get update && apt-get install -y maven wget unzip curl \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Set environment variable for CI (for headless mode)
ENV CI=true

# Download dependencies and run tests
RUN mvn clean test

# Entry point (optional)
CMD ["mvn", "test"]
