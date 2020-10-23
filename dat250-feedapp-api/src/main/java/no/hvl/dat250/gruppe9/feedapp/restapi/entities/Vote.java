package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
public class Vote {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    private String id;

    private String voter;

    @JsonIgnore
    @ManyToOne
    private Poll poll;

    private Boolean answer;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date votetime;

    public Vote() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return voter.equals(vote.voter) &&
                poll.equals(vote.poll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voter, poll);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voter=" + voter +
                ", poll=" + poll +
                ", answer=" + answer +
                ", votetime=" + votetime +
                '}';
    }
}
