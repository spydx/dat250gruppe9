package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Vote {

    @Id
    @GeneratedValue
    private long id;

    private long voterid;

    @JsonIgnore
    @ManyToOne
    private AccountData voter;

    @JsonIgnore
    @ManyToOne
    private Poll poll;

    private Boolean answer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

    public Vote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVoterid() {
        return voterid;
    }

    public void setVoterid(long voterid) {
        this.voterid = voterid;
    }

    public AccountData getVoter() {
        return voter;
    }

    public void setVoter(AccountData voter) {
        this.voter = voter;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public Date getVotetime() {
        return votetime;
    }

    public void setVotetime(Date votetime) {
        this.votetime = votetime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voterid=" + voterid +
                ", voter=" + voter +
                ", poll=" + poll +
                ", answer=" + answer +
                ", votetime=" + votetime +
                '}';
    }
}
