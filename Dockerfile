# Use the base image with Java 17 JDK
FROM eclipse-temurin:17-jdk-alpine

# Set environment variables to prevent interactive prompts during installation
ENV DEBIAN_FRONTEND=noninteractive

# Install Tesseract OCR
RUN apk add --no-cache tesseract-ocr

# Set the volume for temporary files
VOLUME /tmp

# Copy the application JAR file into the container
COPY target/*.jar app.jar

# Default command to run the Java application
ENTRYPOINT ["java","-jar","/app.jar"]

# Optional: Verify Tesseract installation (uncomment to test during build)
RUN tesseract --version && which tesseract