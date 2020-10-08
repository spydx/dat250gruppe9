package no.hvl.dat250.gruppe9.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.*;

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
    @JsonIgnore
    private Set<FeedPoll> pollsList = new HashSet<>();

    @OneToMany
    @JsonIgnore
    private Set<FeedVotes> votedOn = new HashSet<>();

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
    public Set<FeedPoll> getPollsList() {
        return pollsList;
    }

    public void setPollsList(Set<FeedPoll> pollsList) {
        this.pollsList = pollsList;
    }

    @OneToMany
    public Set<FeedVotes> getVotedOn() {
        return votedOn;
    }

    public void setVotedOn(Set<FeedVotes> votedOn) {
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
