package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedIoTDevice;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedVotes;

import javax.persistence.*;

public class FeedPollResultDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedPollResultDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }

    public FeedPollResult getResult(int id){
        try {
            Query q = manager.createQuery("SELECT result FROM FeedPollResult result WHERE result.id = ?1");
            q.setParameter(1, id);
            return (FeedPollResult) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public void addresult(FeedPollResult result){
        manager.getTransaction().begin();
        manager.persist(result);
        manager.getTransaction().commit();
    }

    public boolean updateResult(int id, int no, int total, int yes){
        try{
            Query q = manager.createQuery("UPDATE FeedPollResult SET no = ?1, total = ?2, yes = ?3 " +
                    "WHERE FeedIoTDevice.id = ?4");
            q.setParameter(1, no);
            q.setParameter(2, total);
            q.setParameter(3, yes);
            q.setParameter(4, id);
            q.executeUpdate();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
