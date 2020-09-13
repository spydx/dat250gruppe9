package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedIoTDevice;
import no.hvl.dat250.gruppe9.entities.FeedPoll;


import javax.persistence.*;
import java.sql.PreparedStatement;
import java.util.List;


public class FeedIoTDeviceDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedIoTDeviceDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }


    public FeedIoTDevice getDevice(int id){
        try {
            Query q = manager.createQuery("SELECT d FROM FeedIoTDevice d WHERE d.id = ?1");
            q.setParameter(1, id);
            return (FeedIoTDevice) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedIoTDevice> getAll(){
        Query q = manager.createQuery("SELECT d FROM FeedIoTDevice d");
        return q.getResultList();
    }

    public boolean deleteDevice(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedIoTDevice WHERE FeedIoTDevice.id = ?1");
            q.setParameter(1, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addDevice(FeedIoTDevice device){
        manager.getTransaction().begin();
        manager.persist(device);
        manager.getTransaction().commit();
    }

    public boolean updateDevice(int id, FeedIoTDevice device){
        try{
            Query q = manager.createQuery("UPDATE FeedIoTDevice SET name = ?1 " +
                                             "WHERE FeedIoTDevice.id = ?2");
            q.setParameter(1, device.getName());
            q.setParameter(2, id);
            q.executeUpdate();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
}
