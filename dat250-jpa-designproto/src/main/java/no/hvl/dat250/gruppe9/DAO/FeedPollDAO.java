package no.hvl.dat250.gruppe9.DAO;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedVotes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class FeedPollDAO {

    private static final String ENTITY_NAME = "feedapp";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager manager;

    public FeedPollDAO(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_NAME);
        this.manager = entityManagerFactory.createEntityManager();
    }

    public FeedPoll getPoll(int id){
        try {
            Query q = manager.createQuery("SELECT poll FROM FeedPoll poll WHERE poll.id = ?1");
            q.setParameter(1, id);
            return (FeedPoll) q.getSingleResult();
        }catch (NoResultException e){
            System.out.println(e);
            return null;
        }
    }

    public List<FeedPoll> getAll(){
        Query q = manager.createQuery("SELECT poll FROM FeedPoll poll");
        return q.getResultList();
    }

    public boolean deletePoll(int id){
        try{
            Query q = manager.createQuery("DELETE FROM FeedPoll WHERE FeedPoll.id = ?1");
            q.setParameter(1, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public void addPoll(FeedPoll poll){
        manager.getTransaction().begin();
        manager.persist(poll);
        manager.getTransaction().commit();
    }

    /**
     * Update poll with id equal to given id and the parameters from the new/existing poll
     * @param id
     * @param poll
     * @return true if success, false otherwise
     */
    public boolean updatePoll(int id, FeedPoll poll){
        try{
            Query q = manager.createQuery("UPDATE FeedPoll SET answerno = ?1, answeryes = ?2, name = ?3, question = ?4, timeend = ?5, timestart = ?6, owner = ?7 " +
                                             "WHERE  id = ?8");
            q.setParameter(1, poll.getAnswerno());
            q.setParameter(2, poll.getAnsweryes());
            q.setParameter(3, poll.getName());
            q.setParameter(4, poll.getQuestion());
            q.setParameter(5, poll.getEndTime());
            q.setParameter(6, poll.getStartTime());
            q.setParameter(7, poll.getOwner());
            q.setParameter(8, id);
            manager.getTransaction().begin();
            q.executeUpdate();
            manager.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            return false;
        }
    }

    public FeedVotes addVote(int id, int userId, boolean answer) {
        // Getting poll
        FeedPoll poll = manager.find(FeedPoll.class,id);
        if (poll == null) return null;
        // Creating vote
        FeedVotes vote = new FeedVotes();
        vote.setVoterid(userId);
        vote.setAnswer(answer);
        // Getting the result
        FeedPollResult result = poll.getPollResult();
        if (result == null) return null;
        // Checking if the vote is already present
        for (FeedVotes currentVotes: result.getVotes()) {
            if (currentVotes.getVoterid() == userId) return null;
        }
        // voting and updating the database
        result.Vote(vote);
        manager.getTransaction().begin();
        manager.persist(vote);
        manager.persist(result);
        manager.getTransaction().commit();
        return vote;
    }

    public FeedVotes deleteVote(int pollId, int userId) {
        FeedPoll poll = manager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getPollResult();
        List<FeedVotes> votes = result.getVotes();
        for (FeedVotes vote: votes) {
            if(vote.getVoterid() == userId) {
                manager.getTransaction().begin();
                votes.remove(vote);
                manager.persist(result);
                manager.remove(vote);
                manager.getTransaction().commit();
                return vote;
            }
        }
        return null;
    }

    public FeedVotes updateVote(int userId, int pollId, boolean answer) {
        FeedPoll poll = manager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getPollResult();
        List<FeedVotes> votes = result.getVotes();
        for (FeedVotes vote: votes) {
            if(vote.getVoterid() == userId) {
                manager.getTransaction().begin();
                vote.setAnswer(answer);
                manager.persist(vote);
                manager.getTransaction().commit();
                return vote;
            }
        }
        return null;
    }

    public List<FeedVotes> getVoteList(int pollId) {
        FeedPoll poll = manager.find(FeedPoll.class,pollId);
        if (poll == null) return null;
        FeedPollResult result = poll.getPollResult();
        if (result == null) return null;
        return result.getVotes();
    }
}
