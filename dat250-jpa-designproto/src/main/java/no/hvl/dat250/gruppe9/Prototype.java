package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.DAO.FeedIoTDeviceDAO;
import no.hvl.dat250.gruppe9.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Prototype {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        System.out.println("Hello FeedApp");
        System.out.println("Lets create FeedApp Persistence");
        entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();
        FeedUser u = new FeedUser();
        u.setFirstname("Kenneth");
        u.setRole(FeedRoles.USER);
        manager.persist(u);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        FeedPoll p = new FeedPoll();
        p.setName("QUestinable");
        p.setQuestion("Hotdog or legs?");
        p.setOwner(u);

        manager.persist(p);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        FeedPollResult r = new FeedPollResult();
        r.setNo(1);
        r.setYes(2);
        r.setTotal(r.getNo() + r.getYes());
        manager.persist(r);

        manager.getTransaction().commit();

        System.out.println(u);
        System.out.println(p);


        /**
         * feedIoTDeviceDAO test
         */

        FeedIoTDeviceDAO iotdevicedao = new FeedIoTDeviceDAO();
        FeedIoTDevice d = new FeedIoTDevice();
        d.setName("Some cool device pro");
        iotdevicedao.addDevice(d);
        System.out.println("Result: " + iotdevicedao.getAll());
    }
}
