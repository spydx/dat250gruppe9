package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PollResultDAO {

    @Autowired
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(PollResultDAO.class);

    public Optional<PollResult> save(PollResult item) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(item);
        } catch (Exception e) {
            logger.error("Save error");
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(item);
        }
    }

    public Optional<PollResult> update(PollResult item) {
        var current = Optional.ofNullable(entityManager.find(PollResult.class, item.getId()));
        if(current.isPresent()) {
            entityManager.merge(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(PollResult.class, item.getId()));
        }
        logger.error("Update error for {}", item);
        return current;
    }

    public Optional<PollResult> delete(PollResult item) {
        try {
            var delete = entityManager.find(PollResult.class, item.getId());
            entityManager.remove(delete);
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            logger.error("Unable to delete {} : {}", item, e);
            return Optional.ofNullable(item);
        }
    }

    public Optional<PollResult> get(String id) {
        return Optional.ofNullable(entityManager.find(PollResult.class, id));
    }

    public Optional<List<PollResult>> getAll() {
        var q = entityManager.createQuery("SELECT pr FROM PollResult pr");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }
}

