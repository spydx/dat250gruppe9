package no.hvl.dat250.gruppe9.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class FeedVotes {

    @Id
    @GeneratedValue
    private long id;

    private long voterid;

    @OneToOne
    private FeedUser voter;

    @OneToOne
    private FeedPoll poll;

    // is true it is option a else option b
    private Boolean answer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

    public FeedUser getVoter() {
        return voter;
    }

    public void setVoter(FeedUser voter) {
        this.voter = voter;
    }

    public FeedPoll getPoll() {
        return poll;
    }

    public void setPoll(FeedPoll poll) {
        this.poll = poll;
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
                ", voterid=" + voterid +
                ", answer=" + answer +
                ", votetime=" + votetime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedVotes feedVotes = (FeedVotes) o;
        return voter.equals(feedVotes.voter) &&
                poll.equals(feedVotes.poll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voter, poll);
    }
}
