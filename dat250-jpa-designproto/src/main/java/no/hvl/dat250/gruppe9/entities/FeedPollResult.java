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

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PollResult{" +
                "yes=" + yes +
                ", no=" + no +
                ", votes=" + total +
                '}';
    }
}
