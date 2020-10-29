package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;

public class IoTDTO {
    private String name;
    private String pollid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPollId() {
        return pollid;
    }

    public void setPollId(String poll) {
        this.pollid = poll;
    }
}

