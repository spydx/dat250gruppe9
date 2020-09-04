package no.hvl.dat250.gruppe9.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FeedPollResult {

    @Id
    @GeneratedValue
    private int id;
    private int yes;
    private int no;
    private int votes;

    public FeedPollResult() {}

    @Override
    public String toString() {
        return "PollResult{" +
                "yes=" + yes +
                ", no=" + no +
                ", votes=" + votes +
                '}';
    }
}
