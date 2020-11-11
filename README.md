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

## Final Presentation 

[Final Presentation](ProgressPresentation/Software_Technology_Project_-_Final_Presentation_-_PollHub.pdf)

## PollHub (FeedApp)

To spin up this setup you have to have installed [Docker](https://www.docker.com/products/docker-desktop)
Make sure you are in the folder that holds the [docker-compose.yml](docker-compose.yml) file.

This spins up 7 containers:

```sh
kenneth@kefo ~/dat250gruppe9> docker-compose up -d
Starting dat250gruppe9_feedapp-iotdevice_1 ... done
Starting dat250gruppe9_feedapp-db_1        ... done
Starting dat250gruppe9_feedapp-messaging_1 ... done
Starting dat250gruppe9_cloud-mongodb_1     ... done
Starting dat250gruppe9_feedaapp-frontend_1 ... done
Starting dat250gruppe9_feedapp-muservice_1 ... done
Starting dat250gruppe9_feedapp-api_1       ... done
kenneth@kefo ~/dat250gruppe9> 
```

* FeedApp FrontEnd =>[http://localhost](http://localhost/)
* FeedApp API =>[http://localhost:8080](http://localhost:8080/)
* FeedApp MySQL => [mysql://localhost:3306](mysql://localhost:3306)
* Cloud MongoDB =>[mongodb://feedapp:mongo@localhost:27017/feedapp/](mongodb://feedapp:mongo@localhost:27017)
* FeedApp Messaging (guest/guest) => [http://localhost:15672](http://localhost:15672)
* FeedApp IoT Device => [http://localhost:81](http://localhost:81/)
* FeedApp MuService => use the Docker Console to view it.

This will create a default setup that has a the following user installed.

* admin@pollhub.no / admin
* iot {iot-test-device} / 1234, username is hardcoded to the "device"
* mongodb: feedapp /mongo
* mysql: feedapp /feedapppassword

To configure the admin password for the service.
Do this in the [application.properties](dat250-feedapp-api/src/main/resources/application.properties)

## Other Documents

* [API HTTP Documentation](doc/api.md)
* [MuService in action](doc/muservice.md)
* [IoT in action](doc/iot-dev.md)