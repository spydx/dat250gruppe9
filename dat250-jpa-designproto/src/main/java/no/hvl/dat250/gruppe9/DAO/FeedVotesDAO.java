package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedIoTDevice;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.entities.FeedVotes;

import javax.persistence.*;
import java.util.List;

public class FeedVotesDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedVotesDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }

    public FeedVotes getVote(int id){
        try {
            Query q = manager.createQuery("SELECT vote FROM FeedVotes vote WHERE vote.id = ?1");
            q.setParameter(1, id);
            return (FeedVotes) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedVotes> getAll(){
        Query q = manager.createQuery("SELECT votes FROM FeedVotes votes");
        return q.getResultList();
    }

    public boolean deleteVotes(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedVotes WHERE id = ?1");
            q.setParameter(1, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addVote(FeedVotes vote){
        manager.getTransaction().begin();
        manager.persist(vote);
        manager.getTransaction().commit();
    }

    public boolean updateVote(int id, FeedVotes votes){
        try{
            Query q = manager.createQuery("UPDATE FeedVotes SET answer = ?1, voterid = ?2, votetime = ?3 " +
                    "WHERE id = ?4");
            q.setParameter(1, votes.getAnswer());
            q.setParameter(2, votes.getVoterid());
            q.setParameter(3, votes.getVotetime());
            q.setParameter(4, id);
            q.executeUpdate();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
