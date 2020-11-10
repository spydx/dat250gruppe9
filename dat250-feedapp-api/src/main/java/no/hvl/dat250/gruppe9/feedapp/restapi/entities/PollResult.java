package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class PollResult implements Serializable {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    private String id;

    private int yes = 0;
    private int nos = 0;
    private int total = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pollresult", fetch = FetchType.EAGER)
    private List<Vote> votes;

    public PollResult() {
    }

    public PollResult(int yes, int nos, int total) {
        this.yes = yes;
        this.nos = nos;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public PollResult addNo() {
        this.nos++;
        this.total++;
        return this;
    }

    public PollResult addYes() {
        this.yes++;
        this.total++;
        return this;
    }
    public void setId(String id) {
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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollResult that = (PollResult) o;
        return total == that.total &&
                id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total);
    }

    @Override
    public String toString() {
        return "PollResult{" +
                "id=" + id +
                ", yes=" + yes +
                ", nos=" + nos +
                ", total=" + total +
                '}';
    }
}
