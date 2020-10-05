package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class FeedPollDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public FeedPollDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public FeedPoll getPoll(long id){
        return entityManager.find(FeedPoll.class, id);
   }

    public List<FeedPoll> getAll(){
        Query q = entityManager.createQuery("SELECT poll FROM FeedPoll poll");
        return q.getResultList();
    }


    //TODO: Should not be void
    public void deletePoll(FeedPoll poll){
        entityManager.getTransaction().begin();
        entityManager.remove(poll);
        entityManager.getTransaction().commit();

    }

    //TODO: missing return type?
    public void addPoll(FeedPoll poll){
        entityManager.getTransaction().begin();
        entityManager.persist(poll);
        entityManager.getTransaction().commit();
    }

    public void updatePoll(FeedPoll poll) {
        entityManager.getTransaction().begin();
        entityManager.merge(poll);
        entityManager.getTransaction().commit();
    }
}
