package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
public class PollResult {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    private String id;

    private int yes;
    private int nos;
    private int total;

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
