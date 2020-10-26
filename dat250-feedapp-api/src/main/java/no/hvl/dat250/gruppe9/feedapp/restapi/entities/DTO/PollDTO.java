package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class PollDTO {

    private long id;

    private String name;
    private String question;

    private Date timestart;

    private Date timeend;

    private Access access;

    private String answeryes;
    private String answerno;

    private String owner;

    public PollDTO() {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
