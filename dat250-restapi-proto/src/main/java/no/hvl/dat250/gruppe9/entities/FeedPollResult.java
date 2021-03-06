package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;

@Entity
public class FeedPollResult {

    @Id
    @GeneratedValue
    private Long id;

    private int yes;
    private int nos;
    private int total;

    public FeedPollResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNos() {
        return nos;
    }

    public void setNos(int nos) {
        this.nos = nos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FeedPollResult{" +
                "id=" + id +
                ", yes=" + yes +
                ", nos=" + nos +
                ", total=" + total +
                '}';
    }
}
