package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ResultService {

    private final FeedPollDAO feedPollDAO;
    private final FeedPollResultDAO feedPollResultDAO;

    @Autowired
    public ResultService(FeedPollDAO feedPollDAO, FeedPollResultDAO feedPollResultDAO) {
        this.feedPollDAO = feedPollDAO;
        this.feedPollResultDAO = feedPollResultDAO;
    }
    public List<FeedPollResult> getAllResult() {
        return feedPollResultDAO.getAllResult();
    }

    public FeedPollResult getResult(Long id) {
        if(feedPollDAO.getPoll(id).getFeedPollResult() == null) {
            return generateResult(feedPollDAO.getPoll(id));
        } else {
            return feedPollDAO.getPoll(id).getFeedPollResult();
        }
    }

    public boolean deleteResult(long id) {

        var p = feedPollResultDAO.getResult(id);
        if(p != null) {
            feedPollResultDAO.deleteResult(p);
            return true;
        }
        return false;
    }

    private FeedPollResult generateResult(FeedPoll poll) {
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
