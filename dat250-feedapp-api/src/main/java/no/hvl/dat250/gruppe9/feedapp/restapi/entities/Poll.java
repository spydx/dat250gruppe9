package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
public class Poll {

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
    private Access access;

    private String answeryes;
    private String answerno;

    @ManyToOne
    @JsonIgnore
    private AccountData owner;

    @OneToMany
    @JsonIgnore
    private Set<Vote> votes;

    @OneToOne
    @JsonIgnore
    private PollResult pollResult;

    @OneToMany
    @JsonIgnore
    private Set<IoT> connectedDevices;

    public Poll() {
    }

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

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
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

    //TODO: Check what JSON returns
    public long getOwner() {
        return owner.getId();
    }

    public void setOwner(AccountData owner) {
        this.owner = owner;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public PollResult getPollResult() {
        return pollResult;
    }

    public void setPollResult(PollResult pollResult) {
        this.pollResult = pollResult;
    }

    public Set<IoT> getConnectedDevices() {
        return connectedDevices;
    }

    public void setConnectedDevices(Set<IoT> connectedDevices) {
        this.connectedDevices = connectedDevices;
    }


    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", timestart=" + timestart +
                ", timeend=" + timeend +
                ", access=" + access +
                ", answeryes='" + answeryes + '\'' +
                ", answerno='" + answerno + '\'' +
                ", owner=" + owner +
                ", votes=" + votes +
                //", pollResult=" + pollResult +
                ", connectedDevices=" + connectedDevices +
                '}';
    }
}
