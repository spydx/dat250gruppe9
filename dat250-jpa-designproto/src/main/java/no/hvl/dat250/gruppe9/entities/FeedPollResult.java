package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class FeedPollResult {

    @Id
    @GeneratedValue
    private int id;
    private int yes;
    private int no;
    private int total;

    @OneToMany
    private List<FeedVotes> votes;

    public FeedPollResult() {}

    @Override
    public String toString() {
        return "PollResult{" +
                "yes=" + yes +
                ", no=" + no +
                ", votes=" + total +
                '}';
    }
}
