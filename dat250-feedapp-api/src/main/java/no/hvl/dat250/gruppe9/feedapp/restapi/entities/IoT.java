package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class IoT {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    @OneToOne
    private Poll connectedPoll;

    public IoT() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Poll getConnectedPoll() {
        return connectedPoll;
    }

    public void setConnectedPoll(Poll connectedPoll) {
        this.connectedPoll = connectedPoll;
    }

}
