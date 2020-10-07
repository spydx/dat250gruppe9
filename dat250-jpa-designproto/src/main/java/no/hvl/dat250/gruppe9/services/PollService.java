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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PollService {

    private final FeedPollDAO feedPollDAO;
    private final FeedPollResultDAO feedPollResultDAO;
    private final FeedVotesDAO feedVotesDAO;
    private final FeedUserDAO feedUserDAO;


    @Autowired
    public PollService(
            FeedPollDAO feedPollDAO,
            FeedPollResultDAO feedPollResultDAO,
            FeedVotesDAO feedVotesDAO, FeedUserDAO feedUserDAO) {
        this.feedPollDAO = feedPollDAO;
        this.feedPollResultDAO = feedPollResultDAO;
        this.feedVotesDAO = feedVotesDAO;
        this.feedUserDAO = feedUserDAO;
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
        return generateResult(poll);
    }

    private boolean hasPollResult(Long id) {
        var p = feedPollDAO.getPoll(id);
        return p.getFeedPollResult() != null;
    }

    public FeedPoll addPoll(FeedPoll newPoll){
        feedPollDAO.addPoll(newPoll);
        var user = feedUserDAO.getUser(newPoll.getOwner());
        user.getPollsList().add(newPoll);
        feedUserDAO.updateUser(user);
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

    public FeedVotes addVote(FeedVotes vote, long pollid, long userid){
        var poll = getById(pollid);

        FeedUser user = feedUserDAO.getUser(userid);
        if(poll != null && !poll.getVotebyVoter(vote.getVoterid())) {
            for (FeedVotes v: poll.getVotes()) {
                if (v.getVoter().getId() == userid) return null;
            }
            poll.getVotes().add(vote);
            user.getVotedOn().add(vote);
            vote.setVoter(user);
            vote.setPoll(poll);
            feedVotesDAO.addVote(vote);
            feedUserDAO.updateUser(user);
            feedPollDAO.updatePoll(poll);
            return vote;
        }
        return null;

    }

    //TODO: if vote is null then the result will be wrong
    public FeedPollResult generateResult(FeedPoll poll) {
        if (poll.getFeedPollResult() == null) {
            poll.setFeedPollResult(new FeedPollResult());
        }
        Set<FeedVotes> votes = poll.getVotes();
        int total = 0;
        int yesvotes = 0;
        for(FeedVotes v : votes ) {
            if (v == null) continue;
            if(Boolean.TRUE.equals(v.getAnswer()))
                yesvotes++;
            total++;
        }

        int novotes = total-yesvotes;
        poll.getFeedPollResult().setYes(yesvotes);
        poll.getFeedPollResult().setNos(novotes);
        poll.getFeedPollResult().setTotal(total);

        feedPollDAO.updatePoll(poll);

        return poll.getFeedPollResult();
    }
}
