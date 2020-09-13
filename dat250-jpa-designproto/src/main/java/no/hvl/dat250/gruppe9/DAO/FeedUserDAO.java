package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedIoTDevice;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedRoles;
import no.hvl.dat250.gruppe9.entities.FeedUser;

import javax.persistence.*;
import java.util.List;

public class FeedUserDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedUserDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }

    public FeedUser getUser(int id){
        try {
            Query q = manager.createQuery("SELECT user FROM FeedUser user WHERE user.id = ?1");
            q.setParameter(1, id);
            return (FeedUser) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedUser> getAll(){
        Query q = manager.createQuery("SELECT user FROM FeedUser user");
        return q.getResultList();
    }

    public boolean deleteUser(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedUser WHERE id = ?1");
            q.setParameter(1, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addUser(FeedUser user){
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    public boolean updateUser(int id, FeedUser user, String newPassword){
        try{
            Query q = manager.createQuery("UPDATE FeedUser SET email = ?1, firstname = ?2, lastname = ?3, password = ?4, role = ?5, votedOn = ?6 " +
                    "WHERE id = ?7");
            q.setParameter(1, user.getEmail());
            q.setParameter(2, user.getFirstname());
            q.setParameter(3, user.getLastname());
            q.setParameter(4, newPassword);
            q.setParameter(4, user.getRole());
            q.setParameter(6, user.getVotedOn());
            q.setParameter(7, id);
            q.executeUpdate();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
