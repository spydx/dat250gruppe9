package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class VoteDOA {

    private final EntityManager entityManager;

    @Autowired
    public VoteDOA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Vote> save(Vote item) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(item);
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
    }

    public Optional<Vote> update(Vote item) {
        var current = Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        entityManager.getTransaction().begin();
        if(current.isPresent()) {
            entityManager.merge(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        }
        entityManager.getTransaction().rollback();
        return current;
    }

    public Optional<Vote> delete(Vote item) {
        entityManager.getTransaction().begin();
        try {
            var delete = entityManager.find(Vote.class, item.getId());
            entityManager.remove(delete);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            System.out.println("Unable to delete: " + item + " " + e.toString());
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(item);
        }
    }

    public Optional<Vote> get(Long id) {
        return Optional.ofNullable(entityManager.find(Vote.class, id));
    }

    public Optional<List<Vote>> getAll() {
        var q = entityManager.createQuery("SELECT v from Vote v");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }
}