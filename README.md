# Spring Boot Application Project

This repository contains a Spring Boot application project that utilizes various features and technologies such as Java 17, Jakarta, Spring Profiles, log4j2, Problem Detail, Observability, HttpExchange, Integration Testing using TestContainers, and Spring Security with JWT Token.

## Prerequisites

Before running the application, ensure that you have the following prerequisites installed:

- Java 17
- Maven
- Docker (for running integration tests with TestContainers)

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository:

   ```shell
   git clone https://github.com/sauravkumarshah/spring-boot-3.x-features.git
   cd spring-boot-3.x-features
   ```

2. Build the project using Maven:

   ```shell
   mvn clean install
   ```

3. Run the application:

   ```shell
   java -jar target/application.jar
   ```

   This will start the Spring Boot application on the default port.

4. Access the application:

   Open a web browser and navigate to `http://localhost:8080`.

## Configuration

The application can be configured using Spring Profiles. The following profiles are available:

- `dev` - Development environment configuration
- `prod` - Production environment configuration

To activate a specific profile, update the pom.xml file by uncommenting the desired profile in the `<profiles>` section. Make sure to comment out or remove other profiles if not needed.

## Logging

The application uses log4j2 for logging. The log configuration can be found in the `logback-spring.xml` file. Customize the logging configuration based on your requirements.

## Problem Detail

The application utilizes the Problem Detail specification for reporting errors and exceptions in a standardized format. This allows for better communication of error details between clients and the server.

## Observability

To enable observability, the application integrates with observability tools such as Prometheus and Grafana. The configuration for these tools can be found in the `application.yml` file. Customize the configuration as needed to connect to your observability stack.

## HttpExchange

The application leverages the HttpExchange library for handling HTTP requests and responses. This library provides a more flexible and low-level approach for handling HTTP interactions.

## Integration Testing Using TestContainers

The application includes integration tests that utilize TestContainers. TestContainers provides lightweight, throwaway instances of Docker containers for use in testing. These containers can be used to test components that rely on external dependencies such as databases, message brokers, etc.

To run the integration tests, ensure that Docker is installed and running, and then execute the following command:

```shell
mvn verify
```

This will start the necessary Docker containers, run the integration tests, and provide the test results.

## Spring Security with JWT Token

The application implements Spring Security for authentication and authorization, using JWT (JSON Web Tokens) for token-based authentication. The implementation can be found in the relevant classes and configuration files.

## Contributing

Contributions to this project are welcome. If you find any issues or have any suggestions, please open an issue or submit a pull request.

---

Feel free to customize this README.md file based on your specific project requirements and add any additional sections or instructions as needed.
