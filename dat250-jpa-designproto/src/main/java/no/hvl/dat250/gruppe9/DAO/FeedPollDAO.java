package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedPoll;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class FeedPollDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedPollDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }

    public FeedPoll getPoll(int id){
        try {
            Query q = manager.createQuery("SELECT poll FROM FeedPoll poll WHERE poll.id = ?1");
            q.setParameter(1, id);
            return (FeedPoll) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedPoll> getAll(){
        Query q = manager.createQuery("SELECT poll FROM FeedPoll poll");
        return q.getResultList();
    }

    public boolean deletePoll(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedPoll WHERE FeedPoll.id = ?1");
            q.setParameter(1, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addPoll(FeedPoll poll){
        manager.getTransaction().begin();
        manager.persist(poll);
        manager.getTransaction().commit();
    }

    /**
     * Update poll with id equal to given id
     * @param id
     * @param answerNo
     * @param answerYes
     * @param name
     * @param question
     * @param timeEnd
     * @param timeStart
     * @param ownerId
     * @return true if success, false otherwise
     */
    public boolean updatePoll(int id, String answerNo, String answerYes, String name, String question, Date timeEnd, Date timeStart, int ownerId){
        try{
            Query q = manager.createQuery("UPDATE FeedPoll SET answerno = ?1, answeryes = ?2, name = ?3, question = ?4, timeend = ?5, timestart = ?6, owner = ?7 " +
                                             "WHERE  id = ?8");
            q.setParameter(1, answerNo);
            q.setParameter(2, answerYes);
            q.setParameter(3, name);
            q.setParameter(4, question);
            q.setParameter(5, timeEnd);
            q.setParameter(6, timeStart);
            q.setParameter(7, ownerId);
            q.setParameter(8, id);
            q.executeUpdate();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
