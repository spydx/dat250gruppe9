package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class FeedPoll {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String question;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeend;

    @Enumerated(EnumType.STRING)
    private FeedAccess feedaccess;

    private String answeryes;
    private String answerno;

    @OneToOne
    private FeedUser owner;

    @OneToOne
    private FeedPollResult feedPollResult;

    @OneToMany
    private List<FeedIoTDevice> ioTDevicesList;

    public FeedPoll() {

    }

    public String getAnsweryes() {
        return answeryes;
    }

    public void setAnsweryes(String answeryes) {
        this.answeryes = answeryes;
    }

    public String getAnswerno() {
        return answerno;
    }

    public void setAnswerno(String answerno) {
        this.answerno = answerno;
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

    public void setEndTime(Date timeend) {
        this.timeend = timeend;
    }

    public void setStartTime(Date timestart) {
        this.timestart = timestart;
    }

    public Date getStartTime() {
        return timestart;
    }

    public Date getEndTime() {
        return timeend;
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

    public void setFeedAccess(FeedAccess feedAccess) {
        this.feedaccess = feedAccess;
    }

    public FeedAccess getFeedAccess() {
        return feedaccess;
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
