package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Profile implements Serializable {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid2")
    private String id;

    private String firstname;
    private String lastname;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private Account account;

    @JsonIgnore
    @OneToMany()
    private Set<Poll> pollList = new HashSet<>();

    @JsonIgnore
    @OneToMany
    private Set<Vote> votedOn = new HashSet<>();

    public Profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Poll> getPollList() {
        return pollList;
    }

    public void setPollList(Set<Poll> pollList) {
        this.pollList = pollList;
    }

    public Set<Vote> getVotedOn() {
        return votedOn;
    }

    public void setVotedOn(Set<Vote> votedOn) {
        this.votedOn = votedOn;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", account=" + account +
                ", pollList=" + pollList.size() +
                ", votedOn=" + votedOn.size() +
                '}';
    }
}
