package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.DAO.*;
import no.hvl.dat250.gruppe9.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLOutput;
import java.util.Date;

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

        System.out.println("----------------------------------------------------------------");


        /**
         * feedIoTDeviceDAO test
         */

        FeedIoTDeviceDAO iotdevicedao = new FeedIoTDeviceDAO();
        FeedIoTDevice d = new FeedIoTDevice();
        d.setName("Some stupid device pro");
        iotdevicedao.addDevice(d);
        FeedIoTDevice newdevice = new FeedIoTDevice();
        newdevice.setName("Updated");
        iotdevicedao.updateDevice(4, newdevice);
        System.out.println("Devices: " + iotdevicedao.getAll());

        System.out.println("----------------------------------------------------------------");

        /**
         * FeedPollDAO test
         */

        FeedPollDAO polldao = new FeedPollDAO();
        FeedPoll poll = new FeedPoll();
        poll.setAnswerno("No");
        poll.setAnsweryes("Yes");
        poll.setName("First poll");
        poll.setQuestion("Hvor dårlig er Andrè i progging?");
        poll.setEndTime(new Date());
        poll.setStartTime(new Date());

        polldao.addPoll(poll);
        FeedPoll newPoll = new FeedPoll();
        newPoll.setAnswerno("Updated");
        polldao.updatePoll(302, newPoll);

        System.out.println("Polls: " + polldao.getAll());

        System.out.println("----------------------------------------------------------------");

        /**
         * FeedPollResultDAO test
         */

        FeedPollResultDAO resultdao = new FeedPollResultDAO();
        FeedPollResult result = new FeedPollResult();
        result.setNo(10);
        result.setTotal(100);
        result.setYes(90);

        resultdao.addresult(result);
        FeedPollResult newresult = new FeedPollResult();
        resultdao.updateResult(3, newresult);

        System.out.println("Results: " + resultdao.getResult(3));

        System.out.println("----------------------------------------------------------------");

        /**
         * FeedUserDAO test
         */
        FeedUserDAO userDAO = new FeedUserDAO();
        FeedUser user = new FeedUser();
        user.setEmail("example@hotmail.com");
        user.setFirstname("Ola");
        user.setLastname("Nordmann");
        user.setPassword("abc123");
        user.setRole(FeedRoles.USER);

        userDAO.addUser(user);
        FeedUser newUser = new FeedUser();
        userDAO.updateUser(601, newUser, "oppdatert passord");

        System.out.println("Users: " + userDAO.getAll());

        System.out.println("----------------------------------------------------------------");

        /**
         * FeedVotesDAO test
         */
        FeedVotesDAO votesDAO = new FeedVotesDAO();

        FeedVotes newVote = new FeedVotes();
        newVote.setVoterid(1058);
        votesDAO.updateVote(1058, newVote);


        System.out.println("Votes: " + votesDAO.getAll());







    }
}
