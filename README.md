# About

--------------------------

A Quiz Game made in Spring boot and Vaadin, with the purpose of learning Vaadin. The Quiz questions are fetched from the Trivia API,
and it is possible to choose category and difficulty.
It is also possible to enter players and save them in a database. For this I am using a Postgres in a Docker container.
Using OAuth2 third-party authentication using Google for security (although neither the Google client ID nor the client secret are avaliable here).
I have also added a Dockerfile to dockerize the application, and docker-compose for starting the Postgres database and the application.

-------------------

### Build the JAR-file

Build the JAR-file (Windows): 

`mvn clean package -Pproduction`

### Docker-compose

Run the application using Docker compose:
`docker-compose up`

This would require a Google client ID and a Google client secret, set as environment variables.
