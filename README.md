# springboot-banking-app


Banking Springboot app created for DKB Code Factory coding challenge

## Requirements

For building and running the application you need:

- JDK 1.11
- [Maven 3](https://maven.apache.org)

## Running the application locally

Default port is 8080

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `ie.shanequaid.banking.configuration` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Testing and Integration details



on a successful maven build a swagger document banking.yaml will be created

```shell
mvn clean install
```

This can either be used to generate a client or to create a postman collection

For testing purposes I have also added in a springfox swagger ui library.
This enables a webpage http://localhost:8080/swagger-ui/ in specific environments that will display all necessary endpoint information and provide the ability to hit those endpoints.




