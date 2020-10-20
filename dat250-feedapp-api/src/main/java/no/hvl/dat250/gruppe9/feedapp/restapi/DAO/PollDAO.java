package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import javax.xml.catalog.Catalog;
import java.util.List;
import java.util.Optional;

@Repository
public class PollDAO {

    private final EntityManager entityManager;

    @Autowired
    public PollDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Poll> get(Long id) {
        return Optional.ofNullable(entityManager.find(Poll.class, id));

    }

    public Optional<Poll> save(Poll item) {
        entityManager.getTransaction().begin();
        try{
            entityManager.persist(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(item);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
    }

    public Optional<Poll> update(Poll item) {
        var current = Optional.ofNullable(entityManager.find(Poll.class, item.getId()));
        if(current.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.merge(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(Poll.class, item.getId()));
        }
        return current;
    }

    public Optional<Poll> delete(Poll item) {
        var todelete = entityManager.find(Poll.class, item.getId());
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(todelete);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(todelete);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Poll>> getAll() {
        var q = Optional.ofNullable(entityManager.createQuery("SELECT p FROM Poll p"));
        if(q.isPresent()) {
            var res = q.get().getResultList();
            return Optional.ofNullable(res);
        }
        return Optional.empty();
    }
}
