package no.hvl.dat250.gruppe9.feedapp.restapi.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleEnum name;

    public Roles() {
    }

    public Roles(RoleEnum r) {
        this.name = r;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
