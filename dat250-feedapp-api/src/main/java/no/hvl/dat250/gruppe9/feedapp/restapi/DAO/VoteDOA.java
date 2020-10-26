package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class VoteDOA {

    private final EntityManager entityManager;

    @Autowired
    public VoteDOA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Vote> save(Vote item) {
        try {
            entityManager.persist(item);
            return Optional.ofNullable(item);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Vote> update(Vote item) {
        var current = Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        if(current.isPresent()) {
            entityManager.merge(item);
            return Optional.ofNullable(entityManager.find(Vote.class, item.getId()));
        }
        return current;
    }

    public Optional<Vote> delete(Vote item) {
        try {
            var delete = entityManager.find(Vote.class, item.getId());
            entityManager.remove(delete);
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            System.out.println("Unable to delete: " + item + " " + e.toString());
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
        //var q = entityManager.createQuery("SELECT v from Vote v");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }

    public Optional<List<Vote>> getVoteUserPoll(String pollid) {
        var q = entityManager
                .createQuery("SELECT v From Vote v where v.poll.id = :polluuid", Vote.class)
                .setParameter("polluuid", pollid);

        return Optional.ofNullable(q.getResultList());
    }
}