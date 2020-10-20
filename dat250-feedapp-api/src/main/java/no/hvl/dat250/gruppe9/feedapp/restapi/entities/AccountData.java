package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class AccountData implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String firstname;
    private String lastname;

    @OneToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany
    @JsonIgnore
    private Set<Poll> pollList = new HashSet<>();

    @OneToMany
    @JsonIgnore
    private Set<Vote> votedOn = new HashSet<>();

    public AccountData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getAccount() {
        return account.getEmail();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
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
        return "AccountData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", account=" + account.getEmail() +
                ", role=" + role +
                ", pollList=" + pollList +
                ", votedOn=" + votedOn +
                '}';
    }
}
