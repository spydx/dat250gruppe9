package no.hvl.dat250.gruppe9.controllers;

import java.util.List;

import no.hvl.dat250.gruppe9.entities.FeedVotes;
import no.hvl.dat250.gruppe9.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/")
    public List<FeedVotes> getAll() {
        return voteService.getAll();
    }
    /*
    FeedVotesDAO votesDAO = new FeedVotesDAO();
    FeedPollDAO pollDAO = new FeedPollDAO();

    @GetMapping("/getVotes")
    public List<FeedVotes> getVotes() {
        return votesDAO.getAll();
    }

    @GetMapping("/getPollVotes")
    public List<FeedVotes> getPollVotes(@RequestParam(value = "poll_id") int pollId) {
        return pollDAO.getVoteList(pollId);
    }

    @DeleteMapping("/deleteVote")
    public FeedVotes deleteVote(@RequestParam(value = "user_id") int userId ,
                           @RequestParam(value = "poll_id") int pollId) {
        return pollDAO.deleteVote(pollId,userId);
    }

    @PostMapping("/postVote")
    public FeedVotes postVote(@RequestParam(value = "poll_id") int pollId ,
                     @RequestParam(value = "user_id") int userId ,
                     @RequestParam(value = "answer") boolean answer) {
        return pollDAO.addVote(pollId, userId, answer);
    }

    @PutMapping("putVote")
    public FeedVotes putVote(@RequestParam(value = "user_id") int userId ,
                             @RequestParam(value = "poll_id") int pollId ,
                             @RequestParam(value = "answer") boolean answer) {
        return pollDAO.updateVote(userId, pollId, answer);
    }

     */
}