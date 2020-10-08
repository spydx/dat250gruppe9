package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class FeedPoll {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String question;

    @OneToOne
    private FeedUser owner;

    @OneToOne
    private FeedPollResult feedPollResult;

    @OneToMany
    private List<FeedIoTDevice> ioTDevicesList;

    public FeedPoll() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setOwner(FeedUser owner) {
        this.owner = owner;
    }

    public FeedUser getOwner() {
        return owner;
    }

    public void setPollResult(FeedPollResult feedPollResult) {
        this.feedPollResult = feedPollResult;
    }

    public FeedPollResult getPollResult() {
        return feedPollResult;
    }

    public void setIoTDevicesList(List<FeedIoTDevice> ioTDevicesList) {
        this.ioTDevicesList = ioTDevicesList;
    }

    public List<FeedIoTDevice> getIoTDevicesList() {
        return ioTDevicesList;
    }

    @Override
    public String toString() {
        return "Poll {" +
                "Name='" + name + '\'' +
                ", Question='" + question + '\'' +
                ", PollResult=" + feedPollResult +
                '}';
    }
}
