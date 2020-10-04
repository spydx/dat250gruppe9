package no.hvl.dat250.gruppe9.DAO;

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

    public boolean deleteVotes(int id){
        try{
            Query q = entityManager.createQuery("DELETE FROM FeedVotes WHERE id = ?1");
            q.setParameter(1, id);
            entityManager.getTransaction().begin();
            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addVote(FeedVotes vote){
        entityManager.getTransaction().begin();
        entityManager.persist(vote);
        entityManager.getTransaction().commit();
    }

    public boolean updateVote(int id, FeedVotes votes){
        try{
            Query q = entityManager.createQuery("UPDATE FeedVotes SET answer = ?1, voterid = ?2, votetime = ?3 " +
                    "WHERE id = ?4");
            q.setParameter(1, votes.getAnswer());
            q.setParameter(2, votes.getVoterid());
            q.setParameter(3, votes.getVotetime());
            q.setParameter(4, id);
            entityManager.getTransaction().begin();
            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
