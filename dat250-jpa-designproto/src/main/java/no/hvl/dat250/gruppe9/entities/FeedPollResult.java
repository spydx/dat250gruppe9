package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class FeedPollResult {

    @Id
    @GeneratedValue
    private int id;

    private int yes;
    private int nos;
    private int total;

    @OneToMany
    private List<FeedVotes> votes;

    public FeedPollResult() {}

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return nos;
    }

    public void setNo(int no) {
        this.nos = no;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void Vote(FeedVotes vote) {
        vote.setVotetime(new Date());
        this.votes.add(vote);
    }

    @Override
    public String toString() {
        return "PollResult{" +
                "yes=" + yes +
                ", no=" + nos +
                ", votes=" + total +
                '}';
    }
}
