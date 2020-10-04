package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final FeedVotesDAO feedVotesDAO;

    @Autowired
    public VoteService(FeedVotesDAO feedVotesDAO) {
        this.feedVotesDAO = feedVotesDAO;
    }

    public List<FeedVotes> getAll() {
        return feedVotesDAO.getAll();
    }
}
