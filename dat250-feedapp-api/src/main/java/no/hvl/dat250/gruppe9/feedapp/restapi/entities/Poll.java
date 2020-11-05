package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


@Entity
public class Poll {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    private String id;

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


    @ManyToOne(cascade = CascadeType.ALL)
    private Profile owner;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PollResult pollResult;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<IoT> connectedDevices = new HashSet<>();

    public Poll() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return id.equals(poll.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", timestart=" + timestart +
                ", timeend=" + timeend +
                ", access=" + access +
                ", answeryes='" + answeryes + '\'' +
                ", answerno='" + answerno + '\'' +
                ", owner=" + owner.toString() +
                ", pollResult=" + pollResult +
                ", connectedDevices=" + connectedDevices.size() +
                '}';
    }
}
