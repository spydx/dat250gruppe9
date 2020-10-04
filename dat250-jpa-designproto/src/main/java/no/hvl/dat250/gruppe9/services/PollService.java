package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    private final FeedPollDAO feedPollDAO;

    @Autowired
    public PollService(FeedPollDAO feedPollDAO) {
        this.feedPollDAO = feedPollDAO;
    }

    public List<FeedPoll> getAll() {
        return feedPollDAO.getAll();
    }

}
