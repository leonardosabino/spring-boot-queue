## Introduction

Here are some examples of how to use queues in a spring boot application.

## Requirements

To build and execute the project you'll need:

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://docs.docker.com/install/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

Clone the project [spring-boot-queue](https://github.com/leonardosabino/spring-boot-queue) 
and prepare your workspace.

### Build and tests

- Compile and run tests:
    ```
    .\mvnw clean install
    ```
- Execute the project:
    ```
    ./mvnw spring-boot:run
    ```

### Kafka Configuration

- To enable Kafka, you'll need to set `kafka.enable` to true in `application.properties`:
    ```
    kafka.enable=true
    ```
- After that, make sure to start the Kafka from the `docker-compose.kafka.yml` in `{ROOT_PROJECT}/docker/` with the command:
    ```
    docker-compose -f docker-compose.kafka.yml up
    ```
### SQS Configuration

- To enable SQS, you'll need to set `sqs.enable` to true in `application.properties`:
    ```
    sqs.enable=true
    ```
- After that, make sure to start the SQS from the `docker-compose.sqs.yml` in `{ROOT_PROJECT}/docker/` with the command:
    ```
    docker-compose -f docker-compose.sqs.yml up
    ``` 
