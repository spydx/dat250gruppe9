package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;
    private final UserService userService;
    private final VoteService voteService;

    @Autowired
    public PollController(PollService pollService, UserService userService, VoteService voteService) {
        this.pollService = pollService;
        this.userService = userService;
        this.voteService = voteService;
    }


    //TODO: Show ALl for PUBLIC, show all for Logged in user, take away PRIVATE.
    @GetMapping("/")
    public ResponseEntity<List<Poll>> getAllPolls() {
        var res = pollService.getAllPublic();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Poll> createPoll(
            @RequestBody PollDTO newPoll) {
        var res = pollService.addPoll(newPoll);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{pollid}")
    public ResponseEntity<Poll> pollById(@PathVariable("pollid") final Long id)
    {
        var res = pollService.getPoll(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{pollid}/owner")
    public ResponseEntity<Profile> getOwner(@PathVariable("pollid") final Long id) {
        var poll = pollService.getPoll(id);
        if(poll.isPresent()) {
            var oid = poll.get().getOwner();
            //var owner = userService.getProfile(oid);
            return new ResponseEntity<>(oid, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<Poll> deletePoll(@PathVariable("pollId") final Long id) {
        var deleted = pollService.deletePoll(id);
        if(deleted.isPresent())
            return new ResponseEntity<>(deleted.get(),HttpStatus.OK);

        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{pollId}/result")
    public ResponseEntity<PollResult> getResult(@PathVariable("pollId") final Long pollId) {
        var poll = pollService.getPoll(pollId);
        if(poll.isPresent()) {
            var res = poll.get().getPollResult();
            return new ResponseEntity<PollResult>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{pollid}/{userid}/vote")
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote,
                                           @PathVariable final Long pollid,
                                           @PathVariable final String userid){
        var poll = pollService.getPoll(pollid);
        var voter = userService.getProfile(userid);
        if(voter.isPresent() && poll.isPresent()) {
            var res = voteService.vote(voter.get(), poll.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
