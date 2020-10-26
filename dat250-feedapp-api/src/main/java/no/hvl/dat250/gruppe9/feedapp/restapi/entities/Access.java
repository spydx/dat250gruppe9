package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

public enum Access {
    PUBLIC, //anyone can vote on it
    PRIVATE, //for registered users
    HIDDEN, //only for members that know the pollID
}
