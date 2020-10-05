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

    @GetMapping(value = "/{pollid}")
    public FeedPoll pollById(@PathVariable("pollid") final Long id)
    {
        return pollService.getById(id);
    }

    @GetMapping(value = "/{pollid}/owner") //TODO: this also return the owners password!!
    public FeedUser getOwner(@PathVariable("pollid") final Long id) {
        var userid = pollService.getPollOwner(id);
        return userService.getUser(userid);
    }

    @PostMapping(value = "/{pollId}") //TODO: how are we doing this??
    public FeedPoll createPoll(@RequestBody FeedPoll newPoll, Long ownerId) {
        newPoll.setOwner(userService.getUser(ownerId));
        return pollService.addPoll(newPoll);
    }

    @PostMapping(value = "/{pollid}/vote")
    public FeedVotes createVote(@RequestBody FeedVotes vote, @PathVariable Long pollid){
        return pollService.addVote(vote, pollid);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable("pollId") final Long id) {

        if (pollService.deletePoll(id)) {

            return new ResponseEntity<String>("Ok",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No Content",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{pollId}/result") //TODO: add the result to feedpollresult, and update feedpoll to link result to poll.
    public FeedPollResult getResult(@PathVariable("pollId") final Long pollId) {
        var p = pollService.getById(pollId);
        if(p != null) {
            return pollService.generateResult(p);
        }
        return null;
    }
}