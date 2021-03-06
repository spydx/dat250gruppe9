package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import java.util.Date;

public class PollDTO {

    private String name;
    private String question;

    private Date timeend;

    private Access access;

    private String answeryes;
    private String answerno;

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

}
