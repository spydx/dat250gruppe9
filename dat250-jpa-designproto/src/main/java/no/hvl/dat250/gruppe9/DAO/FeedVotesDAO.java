package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class FeedVotesDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public FeedVotesDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public FeedVotes getVote(int id){
        try {
            Query q = entityManager.createQuery("SELECT vote FROM FeedVotes vote WHERE vote.id = ?1");
            q.setParameter(1, id);
            return (FeedVotes) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedVotes> getAll(){
        Query q = entityManager.createQuery("SELECT votes FROM FeedVotes votes");
        return q.getResultList();
    }



    public FeedVotes addVote(FeedVotes vote, long pollid){
        FeedPoll poll = entityManager.find(FeedPoll.class, pollid);

        if(!poll.getVotebyVoter(vote.getVoterid())) {
            poll.getVotes().add(vote);
            entityManager.getTransaction().begin();
            entityManager.merge(poll);
            entityManager.persist(vote);
            entityManager.getTransaction().commit();

            return vote;
        }
        return null;
    }
}
