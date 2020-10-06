package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedUserDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public FeedUserDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public FeedUser getUser(long id){
        return entityManager.find(FeedUser.class, id);
    }

    public List<FeedUser> getAll(){
        Query q = entityManager.createQuery("SELECT user FROM FeedUser user");
        return q.getResultList();
    }

    public FeedUser deleteUser(long id){
        entityManager.getTransaction().begin();
        var u = entityManager.find(FeedUser.class, id);
        if(u != null) {
            FeedUser update = new FeedUser();
            update.setId(u.getId());
            update.setRole(FeedRoles.DELETED);
            entityManager.merge(update);
            entityManager.getTransaction().commit();
            return update;
        }
        entityManager.getTransaction().rollback();
        return null;
    }

    public FeedUser addUser(FeedUser user){
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public FeedUser updateUser(FeedUser user) {

        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public List<FeedPoll> getPollList(int userId) {
        FeedUser user = entityManager.find(FeedUser.class, userId);
        if (user == null) return null;
        return user.getPollsList();
    }

    public List<FeedVotes> getVotedOnList(int userId) {
        FeedUser user = entityManager.find(FeedUser.class, userId);
        if (user == null) return null;
        return user.getVotedOn();
    }

    public FeedUser setVotedOnList(long userId, List<FeedVotes> votes) {
        var user = entityManager.find(FeedUser.class, userId);
        user.setVotedOn(votes);
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public void addUser(String firstName, String lastName, FeedRoles role, String email, String password) {
        FeedUser user = new FeedUser();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setPollsList(new ArrayList<>());
        user.setVotedOn(new ArrayList<>());
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }
}
