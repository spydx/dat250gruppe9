package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedRoles;
import no.hvl.dat250.gruppe9.entities.FeedUser;

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
        u.setName("Kenneth");
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

        System.out.println(u);
        System.out.println(p);



    }
}
