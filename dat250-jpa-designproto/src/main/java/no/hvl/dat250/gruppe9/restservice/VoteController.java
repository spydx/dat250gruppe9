package no.hvl.dat250.gruppe9.restservice;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    FeedVotesDAO votesDAO = new FeedVotesDAO();
    FeedPollDAO pollDAO = new FeedPollDAO();

    @GetMapping("/votes")
    public List<FeedVotes> getVotes() { return votesDAO.getAll(); }

    @PutMapping("/vote")
    public void vote(@RequestParam(value = "id") int id ,
                     @RequestParam(value = "user_id") int userId ,
                     @RequestParam(value = "vote") boolean vote) {
        pollDAO.addVote(id, userId, vote);
    }
}