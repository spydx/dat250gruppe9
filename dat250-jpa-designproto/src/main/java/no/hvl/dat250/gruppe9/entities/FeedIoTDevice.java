package no.hvl.dat250.gruppe9.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @Override
    public String toString() {
        return "IoTDevice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", connectedPoll=" + connectedPoll +
                '}';
    }
}
