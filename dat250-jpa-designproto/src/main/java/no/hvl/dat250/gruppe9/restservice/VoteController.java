package no.hvl.dat250.gruppe9.restservice;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    FeedVotesDAO votesDAO = new FeedVotesDAO();

    @GetMapping("/votes")
    public List<FeedVotes> getVotes() { return votesDAO.getAll(); }
}