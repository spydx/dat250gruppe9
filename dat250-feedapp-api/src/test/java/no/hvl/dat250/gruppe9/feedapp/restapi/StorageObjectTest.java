package no.hvl.dat250.gruppe9.feedapp.restapi;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase
class StorageObjectTest {

    private final int LIMIT = 10;

    @Autowired
    private TestEntityManager entityManager;

    private List<Profile> ul = new ArrayList<>();
    private List<Poll> pl = new ArrayList<>();

    private static Random random = new Random();

    @BeforeEach
    public void setUp() {
        entityManager.clear();
        createUserList();
        createPollList();
    }

    private void createUserList() {
        for (int i = 0; i <= LIMIT; i++) {
            var u = new Profile();
            u.setFirstname("User " + i);
            u.setLastname("lastName" + i);
            var a = new Account();
            a.setEmail("user" + i + "@mail.no");
            a.setRole(Roles.USER);
            a.setProfile(u);
            ul.add(u);
            System.out.println("Created :" + u);
        }
    }

    private void createPollList() {
        for (int i = 0; i <= LIMIT ; i++) {
            var p = new Poll();
            p.setName("PollID " + i);
            p.setAnswerno("No" +i);
            p.setAnsweryes("Yes" +i);
            p.setQuestion("Is this a real poll");
            Date d = new Date();

            p.setTimeend(d);
            var u = ul.get(random.nextInt(LIMIT));
            p.setOwner(u);
            pl.add(p);
        }
    }

    @Test
    public void saveUserList() {
        for (Profile u : ul) {
            System.out.println("Saving : " + u );
            entityManager.persist(u);
        }
    }

    @Test

    public void saveUser() {
        var u = ul.get(1);
        var id = entityManager.persistAndGetId(u);
        var res = entityManager.find(Profile.class, id);
        System.out.println(res);
        assert(u.getFirstname().equals(res.getFirstname()));
    }

    @Test
    public void savePoll() {
        var p = pl.get(1);
        var id = entityManager.persistAndGetId(p);
        var res = entityManager.find(Poll.class, id);
        System.out.println(res);
        assert(p.getName().equals(res.getName()));
    }


    @Test
    public void savePollList() {
        for(Poll p : pl ) {
            System.out.println("Saving : " + p );
            entityManager.persist(p);
        }
    }

    @Test
    public void fetchUser() {
        saveUserList();
        Integer id = 4;
        var longid = Long.parseLong(id.toString());
        var u = entityManager.find(Profile.class,longid);
        System.out.println("Found " + u);

        var existing = ul.get(id-1);

        System.out.println("Exisiting: " + existing);
        assert (existing.getFirstname().equals(u.getFirstname()));
    }

    @Test
    public void fetchPoll() {
        savePollList();
        Integer id = random.nextInt(LIMIT);
        var longid = Long.parseLong(id.toString());

        var p = entityManager.find(Poll.class, longid);
        System.out.println("Found Poll: " + p);

        var exist = pl.get(id-1);
        System.out.println("Exist poll: " + exist);
        assert(exist.getName().equals(p.getName()));
    }

    @Test
    public void voteOnPoll() {
        saveUserList();
        savePollList();

        Integer id = random.nextInt(LIMIT-1);
        Long pollid = Long.parseLong(id.toString());
        var poll = entityManager.find(Poll.class,pollid);

        for (int i = 1; i <= LIMIT; i++) {
            var v = new Vote();
            if(i % 2 == 0)
                v.setAnswer(Boolean.TRUE);
            else
                v.setAnswer(Boolean.FALSE);

            entityManager.persist(v);
        }
    }

}
