# MeLi Challenge

This project exposes two endpoints:

 ### Item
 .../items/{id}
 Get info from two APIS from MercadoLibre and merge the data into a new entity
 ### Health
 .../health
Get the metrics related to the http incoming and outcoming processes on the service server


## Local Start Process
Set the following env variables:
HOST - db host
PORT - db port
USER - db username
PASS - db password
SCHEMA - db schema

run in the "challenge" directory the "mvn install" command to generate the application jar files

In the challenge directory there is a init.sql file to set up the database


## Start app with Docker 
I could not integrate all the services in one single Dockerfile

## Strategy

### Fetch Data

![Model](img/process.png)

### Database Model

![Model](img/DB.png)

### Components

-   Discovery server
-   Api Gateway
-   Item Service

![Model](img/Components.png)

### Technologies
 - Java 8
 - Maven
 - Spring (Cloud, Data, Actuator, Webflux)
 - Eureka
 - Postgres
 - RabbitMQ

## Improvements that could be done
  - Implement a load balancer like Zuul
  - Implement some monitoring tool to improve the performance and capacity of the health task like Prometheus
  - Add a API documentation generator like Swagger
  - User kubernetes to package the application into a Pod

