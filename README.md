# spring-boot-demo

Spring Boot Demo application with a bunch of services as course exercises.

## Endpoints

The project provides the following service endpoints:

- `GET` `/`: Responds with a `Hello World!` message.
- `GET` `/salute`: Responds with a `Kaixo!` message.
- `GET` `/firewell`: Responds with a `Goodbye!` message in japanese.
- Calculator Service:
  - `GET` `/calculator/add?num1=[num1]&num2=[num2]` Summatory of two numbers.
  - `GET` `/calculator/substract?num1=[num1]&num2=[num2]`: Subtracts two numbers.
  - `GET` `/calculator/multiply?num1=[num1]&num2=[num2]`: Multiplies two numbers.
  - `GET` `/calculator/divide?dividend=[num1]&divisor=[num2]`: Divides two numbers.
- People Directory:
  - `GET` `/people`: Emits a JSON with all registered people.
  - `GET` `/people/[id]`: Emits a JSON with the detail of a record given it ID.
  - `GET` `/people/search?initial=[letter]&age=[number]`: Emits a JSON response with the people list matching the given optional parameters.
  - `POST` `/people`: Creates a new person. Attributes are specified as a JSON request body.
- Bootcamp:
  - Students:
    - `GET` `/bootcamp/alumnos`: Emits a JSON with all registered students.
    - `GET` `/bootcamp/alumnos/[id]`: Emits a JSON with the detail of a record given it ID.
    - `GET` `/bootcamp/alumnos/nid.[nid]`: Emits a JSON with the detail of a record given it NID.
    - `POST` `/bootcamp/alumnos`: Creates a new student. Attributes are specified as a JSON request body.
    - `PUT` `/bootcamp/alumnos/[id]`: Updates a record given it ID. Attributes are specified as a JSON request body.
    - `DELETE` `/bootcamp/alumnos/[id]`: Deletes an existent student given it ID.
  - Classrooms:
    - `GET` `/bootcamp/clases`: Emits a JSON with all registered classroom.
    - `GET` `/bootcamp/clases/[id]`: Emits a JSON with the detail of a record given it ID.
    - `GET` `/bootcamp/clases/code.[code]`: Emits a JSON with the detail of a record given it CODE.
    - `POST` `/bootcamp/clases`: Creates a new classroom. Attributes are specified as a JSON request body.
    - `PUT` `/bootcamp/clases/[id]`: Updates a record given it ID. Attributes are specified as a JSON request body.
    - `DELETE` `/bootcamp/clases/[id]`: Deletes an existent classroom given it ID.

## Getting Started

#### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.13/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.13/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.13/reference/htmlsingle/#web)

#### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Custom Error Handling in REST Controllers with Spring Boot](https://thepracticaldeveloper.com/custom-error-handling-rest-controllers-spring-boot/)
* [Spring Boot Starter Request Correlation](https://github.com/stevesaliman/spring-boot-starter-request-correlation)
* [Spring Boot Actuator with Correlation ID](https://www.jvt.me/posts/2022/01/13/spring-boot-actuator-audit/)
* [Validation with Spring Boot - the Complete Guide](https://reflectoring.io/bean-validation-with-spring-boot/)
