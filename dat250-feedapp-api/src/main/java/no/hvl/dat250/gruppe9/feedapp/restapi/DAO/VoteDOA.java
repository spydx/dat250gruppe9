package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
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
public class VoteDOA {

    @Autowired
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(VoteDOA.class);

    public Optional<Vote> save(Vote item) {
        try {
            entityManager.persist(item);
            return Optional.ofNullable(item);
        } catch (Exception e) {
            logger.error("Unable to save");
            return Optional.empty();
        }
    }

    public Optional<Vote> update(Vote item) {
        var current = Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        if(current.isPresent()) {
            entityManager.merge(item);
            return Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        }
        logger.error("Unable to update");
        return current;
    }

    public Optional<Vote> delete(Vote item) {
        try {
            var delete = entityManager.find(Vote.class, item.getId());
            entityManager.remove(delete);
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            logger.error("Unable to delete : {}", item);
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

    public Optional<List<Vote>> getVotesForPoll(String pollid) {
        var q = entityManager
                .createQuery("SELECT v from Vote v where v.poll.id = :polluuid", Vote.class)
                .setParameter("polluuid", pollid);
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }

    public Optional<List<Vote>> getVoteUserPoll(String pollid) {
        var q = entityManager
                .createQuery("SELECT v From Vote v where v.pollresult = :polluuid", Vote.class)
                .setParameter("polluuid", pollid);

        return Optional.ofNullable(q.getResultList());
    }
}