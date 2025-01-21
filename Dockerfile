# Use an official Ubuntu base image
FROM ubuntu:20.04

# Set environment variables to prevent interactive prompts during installation
ENV DEBIAN_FRONTEND=noninteractive

# Update the package list and install dependencies
RUN apt-get update && \
    apt-get install -y \
    wget \
    curl \
    gnupg2 \
    lsb-release \
    software-properties-common \
    build-essential \
    libtool \
    automake \
    autoconf \
    libleptonica-dev \
    tesseract-ocr \
    tesseract-ocr-eng \
    libpng-dev \
    libjpeg-dev \
    libgif-dev \
    libtiff-dev \
    libicu-dev \
    openjdk-17-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*  # Remove unnecessary apt cache

# Set the TESSDATA_PREFIX environment variable to the location of tessdata
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/

# Set the path to include tesseract in the PATH environment variable
ENV PATH=$PATH:/usr/bin

# Copy your Spring Boot application JAR file (make sure to build it first)
COPY target/Doc-Analyzer-0.0.1-SNAPSHOT.jar /app/Doc-Analyzer-0.0.1-SNAPSHOT.jar

# Set the working directory in the container
WORKDIR /app

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "Doc-Analyzer-0.0.1-SNAPSHOT.jar"]