package no.hvl.dat250.gruppe9.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class FeedUser {
    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private FeedRoles role;

    @OneToMany
    private List<FeedPoll> pollsList;

    @OneToMany
    private List<FeedPoll> votedOn;

    public FeedUser() {

    }
    public String getName() {
        return this.firstname + "" + this.lastname;
    }
    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean checkPassword(String password) {
        return this.password == password;
    }

    public void setRole(FeedRoles role) {
        this.role = role;
    }

    public FeedRoles getRole() {
        return role;
    }

    public void setVotedOn(List<FeedPoll> votedOn) {
        this.votedOn = votedOn;
    }

    public List<FeedPoll> getVotedOn() {
        return votedOn;
    }

    public void setPollsList(List<FeedPoll> pollsList) {
        this.pollsList = pollsList;
    }

    public List<FeedPoll> getPollsList() {
        return pollsList;
    }

    @Override
    public String toString() {
        return "FeedUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", pollsList=" + pollsList +
                ", votedOn=" + votedOn +
                '}';
    }
}
