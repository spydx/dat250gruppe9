package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedIoTDevice;


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
        Query q = manager.createQuery("SELECT d FROM FeedIoTDevice d WHERE FeedIoTDevice.id = ?1");
        q.setParameter(1, id);
        return (FeedIoTDevice) q.getSingleResult();
    }

    public List<FeedIoTDevice> getAll(){
        Query q = manager.createQuery("SELECT d FROM FeedIoTDevice d");
        return q.getResultList();
    }

    public boolean deleteDevice(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedIoTDevice WHERE FeedIoTDevice.id = ?1");
            q.setParameter(1, id);
            q.executeUpdate();
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
}
