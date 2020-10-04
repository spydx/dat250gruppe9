package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class FeedPollResultDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public FeedPollResultDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public FeedPollResult getResult(int resultId){
        return entityManager.find(FeedPollResult.class, resultId);
    }

    public FeedPollResult addResult(FeedPollResult result){
        entityManager.getTransaction().begin();
        entityManager.persist(result);
        entityManager.getTransaction().commit();
        return result;
    }

    // delete by nulling the row except the resultId
    public void deleteResult(FeedPollResult result) {
        var update = new FeedPollResult();
        update.setId(result.getId());
        entityManager.getTransaction().begin();
        entityManager.merge(update);
        entityManager.getTransaction().commit();
    }
}
