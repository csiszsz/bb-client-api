# Client API for OpenBank transactions

Simple RESTful API implementation that retrieves transactions from OpenBank sandbox using Java [Spring Boot](http://projects.spring.io/spring-boot/).
Transforms the collected data and exposes to three endpoints:
- Transactions list
- Transaction filter based on transaction type
- The total amount for the transaction type

## Requirements
For building and running the application you need:

- [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven](https://maven.apache.org)
- [Docker](https://www.docker.com)

## Build project 
`mvn clean install`

## Build without tests
`mvn clean install -DskipTests`

## Build Docker image
`docker build -t {image_name} .`

## Run in Docker
`docker run -p 8080:8080 {image_name}`
___
### See [Swagger UI](http://localhost:8080/swagger-ui/) on your localhost for the API docs
