package no.hvl.dat250.gruppe9.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class FeedIoTDevice {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToOne
    private FeedPoll connectedPoll;

    public FeedIoTDevice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeedPoll getConnectedPoll() {
        return connectedPoll;
    }

    public void setConnectedPoll(FeedPoll connectedPoll) {
        this.connectedPoll = connectedPoll;
    }

    @Override
    public String toString() {
        return "FeedIoTDevice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", connectedPoll=" + connectedPoll +
                '}';
    }
}
