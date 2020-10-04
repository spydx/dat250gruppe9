package no.hvl.dat250.gruppe9.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
public class FeedVotes {

    @Id
    @GeneratedValue
    private long id;

    //@Column(unique=true)
    private long voterid;

    // is true it is option a else option b
    private Boolean answer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

    public long getId() {
        return this.id;
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

    public long getVoterid() {
        return voterid;
    }

    public void setVoterid(long voterid) {
        this.voterid = voterid;
    }

    @Override
    public String toString() {
        return "FeedVotes{" +
                "id=" + id +
                ", answer=" + answer +
                ", votetime=" + votetime +
                '}';
    }
}
