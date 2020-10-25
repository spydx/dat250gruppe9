# DAT250 Gruppe 9

Gruppeoppgave for DAT250 H2020

[Andrè Frøseth Jønland](https://github.com/ImGoze)

[Jan-Erik Erstad](https://github.com/Jan-Erik-Erstad)

[Kenneth Fossen](https://github.com/spydx)

[Rune Almåsbakk](https://github.com/runalmaas)

## Design Document

[Design: High-level application design and persistence ](DesignDocument/designdocument.md)

First assignment to design the application.

## Prototyping

[Prototyping: High-level application persistence](dat250-jps-designproto)

Persistent prototyping for the first assignment creating our datamodel for the app.

[Design and Prototyping: Business logic and REST API ](dat250-restapi-proto)

Second assignment where we designed and implemented the RESTAPI w/swagger and the businesslogic in Services.

## FeedApp

To spin up this setup you have to have installed [Docker](https://www.docker.com/products/docker-desktop)
Make sure you are in the folder that holds the [docker-compose.yml](docker-compose.yml) file.

```sh
> docker-compose up -d
> open http://localhost:8080 #to visit the api
> mysql localhost:3306 -u feedapp -p # to visit the database
```

##### Example spinup 

```sh
kenneth@kefo ~/GIT/dat250gruppe9 > docker-compose up -d

Starting dat250gruppe9_feedapp-db_1 ... done
Starting dat250gruppe9_feedapp-api_1 ... done

kenneth@kefo ~/GIT/dat250gruppe9 > docker ps

CONTAINER ID        IMAGE                       COMMAND                  CREATED             STATUS              PORTS                               NAMES
1b3dd08db35b        dat250gruppe9_feedapp-api   "java -cp app:app/li…"   44 minutes ago      Up 4 seconds        0.0.0.0:8080->8080/tcp              dat250gruppe9_feedapp-api_1
22ccd62bd3d2        mysql:latest                "docker-entrypoint.s…"   44 minutes ago      Up 4 seconds        0.0.0.0:3306->3306/tcp, 33060/tcp   dat250gruppe9_feedapp-db_1

kenneth@kefo ~/GIT/dat250gruppe9 >
```

`mysql` means in this case on of the followin commandline tools, DataGrip or MySQL Workbench.

To rebuild and spin it up

```sh
> docker-compose up --build -d

```

This will create a default setup that has a the following user installed.

* admin@pollhub.no / admin

To configure the admin password for the service.
Do this in the [application.properties](dat250-feedapp-api/src/main/resources/application.properties)

### HTTP GET/POST/PUT/DELTE

All `GET` are allowed from all users.
`POST` is allowed to `/api/auth/*`
`POST, PUT, DELETE` is only for authenticated users.

### Login

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json
Cache-Control: no-cache

{
  "email": "kenneth@mail.com",
  "password" : "test123"
}
```

Response:

```http
 cache-control: no-cache, no-store, max-age=0, must-revalidate  
 connection: keep-alive  
 content-type: application/json  
 date: Sun, 25 Oct 2020 22:09:46 GMT  
 expires: 0  
 keep-alive: timeout=60  
 pragma: no-cache  
 transfer-encoding: chunked  
 vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers  
 x-content-type-options: nosniff  
 x-frame-options: DENY  
 x-xss-protection: 1; mode=block
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZjQyZjY5Yi1iZTA0LTQwZDktOTIzOC0yMjllMzA4M2M4Y2IiLCJpYXQiOjE2MDM2NjM3ODYsImV4cCI6MTYwNDI2ODU4Nn0.DG_1TEvqy5QAHyrZo828wEJAq_HS-IfPZM5s1vtUX_lTaMT-6ZJK8NLgRrtuPQdz9tTZbEuMtu3gR-gE4wntEQ",
  "tokenType": "Bearer"
}
```

### Register user

```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json
Cache-Control: no-cache

{
  "email": "kenneth@mail.com",
  "password" : "test123",
  "firstname": "Kenneth",
  "lastname": "Fossen"
}
```

### Change password

Do be able to do this you need to know the ProfileID for the user you want to update.
This situation it is : `ProfileID: e4b8ed12-8cca-4f64-8e3f-efbe195589c7`

Also the user have to be autheticated with a JWT Bearer token, 
to be able to send the update. This is the HTTP HEADER Option:
`Authorization: Bearer <token>`

```http
PUT http://localhost:8080/api/account/e4b8ed12-8cca-4f64-8e3f-efbe195589c7
Content-Type: application/json
Cache-Control: no-cache
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2IxOTQxZi0xYjBhLTQ0OTQtYjAxYy0xMjBjYzBjMjQ2YzAiLCJpYXQiOjE2MDM1NzkwNTAsImV4cCI6MTYwNDE4Mzg1MH0.FB0UAS3MEuIGh4ff4o7vFQy--JDP0oQYHlPQ3arjLdj2COgDLbTkluXnaD-oH1cG120XuioclTGjRmSIpzXckA

{
  "email": "kenneth@mail.com",
  "password" : "newpassword"
}
```

### Create a Poll

**Req:**

* Authenticated
* Have a ownerid (profileID)

This is the HTTP HEADER Option: `Authorization: Bearer <token>`

```http
POST http://localhost:8080/api/polls/
Content-Type: application/json
Cache-Control: no-cache
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4MzFmODNmZi05NjFjLTQ2OWEtYjgzZC0wN2JjNDIwNmQ0MjMiLCJpYXQiOjE2MDM2MDgyNDcsImV4cCI6MTYwNDIxMzA0N30.98tnkeGCiwvZwbBuFt5URpObmgCznUtWjQXVnTQK2u_IvSCeuSjS4ILkL8PZVK4Tb3FCrjDwdNbFHy3q5ZrWJA


{
  "access": "PUBLIC", //can also be PRIVATE or HIDDEN
  "answerno": "stringed",
  "answeryes": "stringa",
  "id": 0,
  "name": "StringyStringString",
  "owner": "6b0feae3-855a-491b-a0f7-9799ff219586",
  "question": "stringy?",
  "timeend": "2020-10-22T21:54:07.062Z",
  "timestart": "2020-10-30T21:54:07.062Z"
}
```

`PUBLIC` anyone can vote on it
`PRIVATE` only for registred users
`HIDDEN` only for members that know the pollID

### Get All (Public)

GET http://localhost:8080/api/polls/
Accept: application/json

### Get a poll by ID

GET http://localhost:8080/api/polls/5facb988-def9-4e69-bbd0-676b517ddd9e
Accept: application/json

### Get a poll Owner

GET http://localhost:8080/api/polls/bae6e5c5-839f-4f8a-98f1-5d2aabf6fd9c/owner
Accept: application/json

### Get a result for a Poll

GET http://localhost:8080/api/polls/bae6e5c5-839f-4f8a-98f1-5d2aabf6fd9c/result
Accept: application/json

### Retrive all Users Profiles

```http
GET http://localhost:8080/api/poll/
Accept: application/json

```

### Vote on a Poll

`PollID` for the poll to Vote on.
Will deduce the User from JWT Token in the HTTP HEADER Option: `Authorization: Bearer <token>`

```http
POST http://localhost:8080/api/poll/353e1708-6ccb-43b2-843e-3328a2451e55/vote/
Content-Type: application/json
Cache-Control: no-cache
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4MzFmODNmZi05NjFjLTQ2OWEtYjgzZC0wN2JjNDIwNmQ0MjMiLCJpYXQiOjE2MDM2MDgyNDcsImV4cCI6MTYwNDIxMzA0N30.98tnkeGCiwvZwbBuFt5URpObmgCznUtWjQXVnTQK2u_IvSCeuSjS4ILkL8PZVK4Tb3FCrjDwdNbFHy3q5ZrWJA

{
  "answer": false
}
```
