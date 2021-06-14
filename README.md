Online Course Scheduling Application
====================

Description
---------------------
This application allows the planning and setup of online courses or trainings.

Functionalities
---------------------
As an ADMIN I can:
* Retrieve the list of courses
* Create new public course session and assign a trainer
* Delete courses and users

As a TRAINER I can:
* Retrieve the list of courses
* Edit existing courses
* See the list of users that participate to a course 

As a BASIC USER / CLIENT I can:
* Retrieve the list of courses
* Create my user account in the application
* Book a course in status “open” when authenticated


Technologies
---------------------
* Java 11
* Maven
* Spring Boot with Spring Web (REST Services), Spring Data JPA, Spring Security
* PostgreSQL
* Junit 5
* Mockito
* Postman
* Swagger

API Documentation
---------------------
The documentation for the API was created using Swagger. 
Run the application and use the following URL: http://localhost:8080/swagger-ui.html

Run the Application Locally
---------------------
Run the application from the IDE; by default it will run on port 8080: http://localhost:8080/

Database configuration
---------------------
The project uses PostgreSQL database.

The database URL, username and password are set in the application.properties file. 
