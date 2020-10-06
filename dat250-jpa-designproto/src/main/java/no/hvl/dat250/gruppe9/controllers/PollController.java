package no.hvl.dat250.gruppe9.controllers;

import jdk.javadoc.doclet.Reporter;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import no.hvl.dat250.gruppe9.services.PollService;
import no.hvl.dat250.gruppe9.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.hvl.dat250.gruppe9.PopulateDBService.userService;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;
    private final UserService userService;

    @Autowired
    public PollController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<FeedPoll> getAllPolls() {
        return pollService.getAll();
    }

    @PostMapping(value = "/")
    public FeedPoll createPoll(@RequestBody FeedPoll newPoll, Long ownerId) {
        newPoll.setOwner(userService.getUser(ownerId));
        return pollService.addPoll(newPoll);
    }

    @GetMapping(value = "/{pollid}")
    public FeedPoll pollById(@PathVariable("pollid") final Long id)
    {
        return pollService.getById(id);
    }

    @GetMapping(value = "/{pollid}/owner")
    public FeedUser getOwner(@PathVariable("pollid") final Long id) {
        var userid = pollService.getPollOwner(id);
        return userService.getUser(userid);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable("pollId") final Long id) {
        if (pollService.deletePoll(id)) {
            return new ResponseEntity<String>("Ok",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No Content",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{pollId}/result")
    public ResponseEntity<String> getResult(@PathVariable("pollId") final Long pollId) {
        var res = pollService.getResult(pollId);
        if (res != null) {
            return new ResponseEntity<String>(res.toString(),HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/{pollid}/vote")
    public ResponseEntity<String> createVote(@RequestBody FeedVotes vote, @PathVariable Long pollid){
        var voted = pollService.addVote(vote, pollid);
        if (voted == null) {
            return new ResponseEntity<String>("Not allowed to vote twice", HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        }
    }
}