package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
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

    @Autowired
    public PollService(FeedPollDAO feedPollDAO, FeedPollResultDAO feedPollResultDAO) {
        this.feedPollDAO = feedPollDAO;
        this.feedPollResultDAO = feedPollResultDAO;
    }

    public List<FeedPoll> getAll() {
        return feedPollDAO.getAll();
    }

    public FeedPoll getById(long id) {
        return feedPollDAO.getPoll(id);
    }

    public FeedUser getPollOwner(long id) {
        var poll =  feedPollDAO.getPoll(id);
        return poll.getOwner();
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
