package no.hvl.dat250.gruppe9.restservice;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteController {
    FeedVotesDAO votesDAO = new FeedVotesDAO();
    FeedPollDAO pollDAO = new FeedPollDAO();

    @GetMapping("/getVotes")
    public List<FeedVotes> getVotes() {
        return votesDAO.getAll();
    }

    @DeleteMapping("/deleteVote")
    public FeedVotes deleteVote(@RequestParam(value = "user_id") int userId ,
                           @RequestParam(value = "poll_id") int pollId) {
        return pollDAO.deleteVote(pollId,userId);
    }

    @PutMapping("/putVote")
    public FeedVotes putVote(@RequestParam(value = "id") int id ,
                     @RequestParam(value = "user_id") int userId ,
                     @RequestParam(value = "vote") boolean vote) {
        return pollDAO.addVote(id, userId, vote);
    }
}