package no.hvl.dat250.gruppe9.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
public class FeedVotes {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique=true)
    private int voterid;

    // is true it is option a else option b
    private Boolean answer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

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

    public int getVoterid() {
        return voterid;
    }

    public void setVoterid(int voterid) {
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
