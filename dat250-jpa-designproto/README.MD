# FeedApp RestAPI


## Swagger UI

http://localhost:8080/swagger-ui.html

## Polls 

For polls we allow the following, GET, POST, DELETE

GET http://localhost:8080/api/polls/ -> List og all Polls

POST http://localhost:8080/api/polls/ 

**POST to create a Poll** 
```json
{
  "answerno": "strasdfing",
  "answeryes": "sasdftring",
  "feedPollResult": {
   
  },
  "feedaccess": "PUBLIC",
  "id": 0,
  "ioTDevicesList": [
    {
      "id": 0,
      "name": "string"
    }
  ],
  "name": "string",
  "owner": null,
  "question": "string",
  "timeend": "2020-10-05T22:56:33.544Z",
  "timestart": "2020-10-05T22:56:33.544Z",
  "votes": []
}
```


Added parameter ownerID = 8, check if it is in the database first.

GET http://localhost:8080/api/polls/{pollId}

DELETE http://localhost:8080/api/polls/{pollId}

GET http://localhost:8080/api/polls/{pollId}/owner

GET http://localhost:8080/api/polls/{pollId}/result

POST http://localhost:8080/api/polls/{pollId}/vote


## Users 
For users we have the following, GET, POST, DELETE, PUT

GET http://localhost:8080/api/users/

POST http://localhost:8080/api/users/
````json 
{
  "email": "lkajsdflk@alksjdflakjsdf.com",
  "firstname": "stringStringesen",
  "id": 0,
  "lastname": "stringTruse",
  "pollsList": null, 
  "role": "USER",
  "votedOn": [
  ]
}
````
GET http://localhost:8080/api/users/{userid}

PUT http://localhost:8080/api/users/{userid}

DELTE http://localhost:8080/api/users/{userid}


## Result

GET http://localhost:8080/api/result/
GET http://localhost:8080/api/result/{pollId}
POST http://localhost:8080/api/result/{pollId}




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