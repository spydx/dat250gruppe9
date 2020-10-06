package no.hvl.dat250.gruppe9.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class FeedUser {
    @Id
    @GeneratedValue
    private long id;

    private String firstname;
    private String lastname;

    @Column(unique=true)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private FeedRoles role;

    @OneToMany
    private List<FeedPoll> pollsList = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<FeedVotes> votedOn = new ArrayList<>();

    public FeedUser() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FeedRoles getRole() {
        return role;
    }

    public void setRole(FeedRoles role) {
        this.role = role;
    }

    @OneToMany
    public List<FeedPoll> getPollsList() {
        return pollsList;
    }

    public void setPollsList(List<FeedPoll> pollsList) {
        this.pollsList = pollsList;
    }

    @OneToMany
    public List<FeedVotes> getVotedOn() {
        return votedOn;
    }

    public void setVotedOn(List<FeedVotes> votedOn) {
        this.votedOn = votedOn;
    }

    @Override
    public String toString() {
        return "FeedUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + null + '\'' +
                ", role=" + role +
                ", pollsList=" + pollsList +
                ", votedOn=" + votedOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedUser feedUser = (FeedUser) o;
        return firstname.equals(feedUser.firstname) &&
                lastname.equals(feedUser.lastname) &&
                email.equals(feedUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email);
    }
}
