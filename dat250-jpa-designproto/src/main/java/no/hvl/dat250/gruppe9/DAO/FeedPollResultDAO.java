package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedPollResult;

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

    public boolean updateResult(int id, FeedPollResult result){
        try{
            Query q = manager.createQuery("UPDATE FeedPollResult SET nos = ?1, total = ?2, yes = ?3 " +
                    "WHERE FeedIoTDevice.id = ?4");
            q.setParameter(1, result.getNo());
            q.setParameter(2, result.getTotal());
            q.setParameter(3, result.getYes());
            q.setParameter(4, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
