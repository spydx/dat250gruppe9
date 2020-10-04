# FeedApp RestAPI


## Swagger UI

http://localhost:8080/swagger-ui.html

## Polls 
http://localhost:8080/polls/

http://localhost:8080/polls/311/

http://localhost:8080/polls/311/owner

## Users 

http://localhost:8080/users/

## Votes

http://localhost:8080/votes/


## Project

### Entities
Definitions of our objects.

### DAO
Data Access Objects
Using the entityManger to store and maintain peristent objects in the database.


### Services

These are handleling the Business Logic for the DAO.
The controllers use this layer to communicate and consume DAO.

### Controllers
Presenting data with JSON or XML if we want.
Collects data from the businesslayer (Services)