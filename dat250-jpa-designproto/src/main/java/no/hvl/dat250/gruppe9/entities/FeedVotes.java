package no.hvl.dat250.gruppe9.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
public class FeedVotes {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private FeedUser voter;

    // is true it is option a else option b
    private Boolean answer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVoterid() {
        return voter.getId();
    }

    public void setVoterid(long voterid) {
        this.voter.setId(voterid);
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
        return "FeedVotes{" +
                "id=" + id +
                ", voterid=" + voter.getId() +
                ", answer=" + answer +
                ", votetime=" + votetime +
                '}';
    }
}
