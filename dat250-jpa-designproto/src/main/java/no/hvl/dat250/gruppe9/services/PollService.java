package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    private final FeedPollDAO feedPollDAO;
    private final FeedPollResultDAO feedPollResultDAO;
    private final FeedVotesDAO feedVotesDAO;


    @Autowired
    public PollService(
            FeedPollDAO feedPollDAO,
            FeedPollResultDAO feedPollResultDAO,
            FeedVotesDAO feedVotesDAO) {
        this.feedPollDAO = feedPollDAO;
        this.feedPollResultDAO = feedPollResultDAO;
        this.feedVotesDAO = feedVotesDAO;
    }

    public List<FeedPoll> getAll() {
        return feedPollDAO.getAll();
    }

    public FeedPoll getById(long id) {
        return feedPollDAO.getPoll(id);
    }

    public long getPollOwner(long id) {
        var poll =  feedPollDAO.getPoll(id);
        return poll.getOwner();
    }

    public FeedPollResult getResult(long pollId) {
        var poll = feedPollDAO.getPoll(pollId);
        return poll.getFeedPollResult();
    }

    private boolean hasPollResult(Long id) {
        var p = feedPollDAO.getPoll(id);
        return p.getFeedPollResult() != null;
    }

    public FeedPoll addPoll(FeedPoll newPoll){
        feedPollDAO.addPoll(newPoll);
        return newPoll;
    }

    public boolean deletePoll(Long id){
        //Todo: check if the user owns the poll , or is an admin
        var tobeDeleted = feedPollDAO.getPoll(id);
        if (tobeDeleted != null) {
            feedPollDAO.deletePoll(tobeDeleted);
            return true;
        }
        return false;
    }

    public FeedVotes addVote(FeedVotes vote, long pollid){
        return feedVotesDAO.addVote(vote, pollid);
    }

    //TODO: if vote is null then the result will be wrong
    public FeedPollResult generateResult(FeedPoll poll) {

        var result = new FeedPollResult();
        List<FeedVotes> votes = poll.getVotes();
        int total = votes.size();
        int yesvotes = 0;
        for(FeedVotes v : votes ) {
            if(Boolean.TRUE.equals(v.getAnswer()))
                yesvotes++;
        }

        int novotes = total-yesvotes;
        result.setYes(yesvotes);
        result.setNos(novotes);
        result.setTotal(total);
        feedPollResultDAO.addResult(result);
        return result;
    }
}
