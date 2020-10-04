package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FeedPollDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public FeedPollDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public FeedPoll getPoll(long id){
        return entityManager.find(FeedPoll.class, id);
   }

    public List<FeedPoll> getAll(){
        Query q = entityManager.createQuery("SELECT poll FROM FeedPoll poll");
        return q.getResultList();
    }


    //TODO: Should not be void
    public void deletePoll(FeedPoll poll){
        entityManager.getTransaction().begin();
        entityManager.remove(poll);
        entityManager.getTransaction().commit();

    }

    //TODO: missing return type?
    public void addPoll(FeedPoll poll){
        entityManager.getTransaction().begin();
        entityManager.persist(poll);
        entityManager.getTransaction().commit();
    }



    /**
     * Update poll with id equal to given id and the parameters from the new/existing poll
     * @param id
     * @param poll
     * @return true if success, false otherwise
     *
    public boolean updatePoll(int id, FeedPoll poll){
        try{
            Query q = entityManager.createQuery("UPDATE FeedPoll SET answerno = ?1, answeryes = ?2, name = ?3, question = ?4, timeend = ?5, timestart = ?6, owner = ?7 " +
                                             "WHERE  id = ?8");
            q.setParameter(1, poll.getAnswerno());
            q.setParameter(2, poll.getAnsweryes());
            q.setParameter(3, poll.getName());
            q.setParameter(4, poll.getQuestion());
            q.setParameter(5, poll.getTimeend());
            q.setParameter(6, poll.getTimestart());
            q.setParameter(7, poll.getOwner());
            q.setParameter(8, id);
            entityManager.getTransaction().begin();
            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }
/*
    public FeedVotes addVote(int id, int userId, boolean answer) {
        // Getting poll
        FeedPoll poll = entityManager.find(FeedPoll.class,id);
        if (poll == null) return null;
        // Creating vote
        FeedVotes vote = new FeedVotes();
        vote.setVoterid(userId);
        vote.setAnswer(answer);
        // Getting the result
        FeedPollResult result = poll.getFeedPollResult();
        if (result == null) return null;
        // Checking if the vote is already present
        for (FeedVotes currentVotes: result.getVotes()) {
            if (currentVotes.getVoterid() == userId) return null;
        }
        // voting and updating the database
        result.Vote(vote);
        entityManager.getTransaction().begin();
        entityManager.persist(vote);
        entityManager.persist(result);
        entityManager.getTransaction().commit();
        return vote;
    }

    public FeedVotes deleteVote(int pollId, int userId) {
        FeedPoll poll = entityManager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getFeedPollResult();
        List<FeedVotes> votes = result.getVotes();
        for (FeedVotes vote: votes) {
            if(vote.getVoterid() == userId) {
                entityManager.getTransaction().begin();
                votes.remove(vote);
                entityManager.persist(result);
                entityManager.remove(vote);
                entityManager.getTransaction().commit();
                return vote;
            }
        }
        return null;
    }

    public FeedVotes updateVote(int userId, int pollId, boolean answer) {
        FeedPoll poll = entityManager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getFeedPollResult();
        List<FeedVotes> votes = result.getVotes();
        for (FeedVotes vote: votes) {
            if(vote.getVoterid() == userId) {
                entityManager.getTransaction().begin();
                vote.setAnswer(answer);
                entityManager.persist(vote);
                entityManager.getTransaction().commit();
                return vote;
            }
        }
        return null;
    }

    public List<FeedVotes> getVoteList(int pollId) {
        FeedPoll poll = entityManager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getFeedPollResult();
        if (result == null) return null;
        return result.getVotes();
    }

    public void addPoll(String pollName, String question, FeedAccess access, String yes, String no, FeedUser user, FeedPollResult result) {
        FeedPoll poll = new FeedPoll();
        poll.setName(pollName);
        poll.setQuestion(question);
        poll.setFeedaccess(access);
        poll.setAnsweryes(yes);
        poll.setAnswerno(no);
        poll.setOwner(user);
        poll.setFeedPollResult(result);
        poll.setTimestart(new Date());
        if (user.getPollsList() == null) user.setPollsList(new ArrayList<>());
        user.getPollsList().add(poll);
        entityManager.getTransaction().begin();
        //manager.persist(user);
        entityManager.persist(poll);
        entityManager.persist(result);
        entityManager.getTransaction().commit();

    }

 */
}
