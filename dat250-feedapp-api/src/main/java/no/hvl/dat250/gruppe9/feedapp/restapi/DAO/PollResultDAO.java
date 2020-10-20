package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class PollResultDAO {

    private final EntityManager entityManager;

    @Autowired
    public PollResultDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<PollResult> save(PollResult item) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(item);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(item);
        }
    }

    public Optional<PollResult> update(PollResult item) {
        var current = Optional.ofNullable(entityManager.find(PollResult.class, item.getId()));
        entityManager.getTransaction().begin();
        if(current.isPresent()) {
            entityManager.merge(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(PollResult.class, item.getId()));
        }
        entityManager.getTransaction().rollback();
        return current;
    }

    public Optional<PollResult> delete(PollResult item) {
        entityManager.getTransaction().begin();
        try {
            var delete = entityManager.find(PollResult.class, item.getId());
            entityManager.remove(delete);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            System.out.println("Unable to delete: " + item + " " + e.toString());
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(item);
        }
    }

    public Optional<PollResult> get(Long id) {
        return Optional.ofNullable(entityManager.find(PollResult.class, id));
    }

    public Optional<List<PollResult>> getAll() {
        var q = entityManager.createQuery("SELECT pr FROM PollResult pr");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }
}

