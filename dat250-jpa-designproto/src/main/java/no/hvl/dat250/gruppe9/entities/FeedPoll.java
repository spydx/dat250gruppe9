package no.hvl.dat250.gruppe9.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class FeedPoll {

    @Id
    @GeneratedValue
    private long id;

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

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<FeedVotes> votes = new ArrayList<>();

    @OneToOne
    private FeedPollResult feedPollResult;

    @OneToMany
    private List<FeedIoTDevice> ioTDevicesList;

    public long getId() {
        return id;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getTimestart() {
        return timestart;
    }

    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    public FeedAccess getFeedaccess() {
        return feedaccess;
    }

    public void setFeedaccess(FeedAccess feedaccess) {
        this.feedaccess = feedaccess;
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

    public FeedUser getOwner() {
        return owner;
    }

    public void setOwner(FeedUser owner) {
        this.owner = owner;
    }

    public List<FeedVotes> getVotes() {
        return votes;
    }

    public void setVotes(List<FeedVotes> votes) {
        this.votes = votes;
    }

    public FeedPollResult getFeedPollResult() {
        return feedPollResult;
    }

    public void setFeedPollResult(FeedPollResult feedPollResult) {
        this.feedPollResult = feedPollResult;
    }

    public List<FeedIoTDevice> getIoTDevicesList() {
        return ioTDevicesList;
    }

    public void setIoTDevicesList(List<FeedIoTDevice> ioTDevicesList) {
        this.ioTDevicesList = ioTDevicesList;
    }

    @Override
    public String toString() {
        return "FeedPoll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", timestart=" + timestart +
                ", timeend=" + timeend +
                ", feedaccess=" + feedaccess +
                ", answeryes='" + answeryes + '\'' +
                ", answerno='" + answerno + '\'' +
                ", owner=" + owner +
                ", votes=" + votes +
                ", feedPollResult=" + feedPollResult +
                ", ioTDevicesList=" + ioTDevicesList +
                '}';
    }
}
