# Happy Shop

This is an example project to give you an idea of my way of working.

## About me
Author: Markus Breckner<p>
LinkedIn: https://www.linkedin.com/in/markus-breckner-981374185/

## Background
Happy Shop is a made up Company which offers some online shopping.
So this project will provide the backend service to provide the following use cases:
- Create and persist a shopping cart
- Get a shopping cart with all items
- Add a new item to the cart
- Remove an item from the cart (Coming soon)
- Change the quantity of a cart item (Coming soon)

## Technical Background

### Languages/Frameworks
- Java
- Spring Boot
- Swagger to show the API Contract

### Architecture
The service was implemented using the <strong>Hexagonal Architecture</strong>
(you might have heard if by other names like Adapters & Ports Architecture, Clean Architecture).
The main idea of this architecture is to provide a well layered architecture with three layers,
where the direction of dependencies should not go from top to bottom like in MVC, but from outside to inside,
where the inside is the domain layer. The domain layer should not have any dependencies to another layer,
because we should focus there on reflecting the domain model and implement all the business rules.
The outer layer are all the adapters (like REST Controllers, Persistence Implementations, Calling external service).
No inner layer should depend on those because we do want to concentrate on our use cases and business rules.

If you are interested you can read more [here](https://reflectoring.io/spring-hexagonal/

## Run locally

### Run with IntelliJ
Open the project with your IDEA like IntelliJ and run the HappyshopApplication

### Run from terminal
Run it from your terminal by calling `./gradlew bootRun`. You need Java 11 to be installed on your machine

### Run it with docker
1) `docker build -t happyshop-docker .`
2) `docker run -p 8080:8080 happyshop-docker`

### Swagger
When the service is running you can call swagger to see all the available endpoints and how to call them:
- OpenAPI Json: http://localhost:8080/v3/api-docs
- Swagger UI: http://localhost:8080/happyshop-swagger-ui

## Improvement ideas

- Use mapstruct for mapping
- Add request/response logging with correlationId
- Use caching
- Use a non-relational DB to persist the data
- Use a second way (beside HTTP Requests) to call the usecase, e.g. Queues

