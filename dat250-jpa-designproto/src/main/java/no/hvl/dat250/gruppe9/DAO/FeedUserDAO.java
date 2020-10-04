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

    public FeedUser getUser(int id){
        return entityManager.find(FeedUser.class, id);
    }

    public List<FeedUser> getAll(){
        Query q = entityManager.createQuery("SELECT user FROM FeedUser user");
        return q.getResultList();
    }

    public boolean deleteUser(int id){
        try{
            Query q = entityManager.createQuery("DELETE FROM FeedUser WHERE id = ?1");
            q.setParameter(1, id);
            entityManager.getTransaction().begin();
            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addUser(FeedUser user){
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public boolean updateUser(int id, FeedUser user, String newPassword){
        try{
            Query q = entityManager.createQuery("UPDATE FeedUser SET email = ?1, firstname = ?2, lastname = ?3, password = ?4, role = ?5" +
                    " WHERE id = ?6");
            q.setParameter(1, user.getEmail());
            q.setParameter(2, user.getFirstname());
            q.setParameter(3, user.getLastname());
            q.setParameter(4, newPassword);
            q.setParameter(5, user.getRole());
            q.setParameter(6, id);
            entityManager.getTransaction().begin();
            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
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
        entityManager.persist(user);
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
