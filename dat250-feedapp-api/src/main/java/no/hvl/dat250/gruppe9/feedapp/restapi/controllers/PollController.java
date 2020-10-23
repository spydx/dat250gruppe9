package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;
    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;

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
    public ResponseEntity<Poll> pollById(@PathVariable("pollid") final String id)
    {
        var res = pollService.getPoll(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{pollid}/owner")
    public ResponseEntity<Profile> getOwner(@PathVariable("pollid") final String id) {
        var poll = pollService.getPoll(id);
        if(poll.isPresent()) {
            var oid = poll.get().getOwner();
            //var owner = userService.getProfile(oid);
            return new ResponseEntity<>(oid, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<Poll> deletePoll(@PathVariable("pollId") final String id) {
        var deleted = pollService.deletePoll(id);
        if(deleted.isPresent())
            return new ResponseEntity<>(deleted.get(),HttpStatus.OK);

        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{pollId}/result")
    public ResponseEntity<PollResult> getResult(@PathVariable("pollId") final String pollId) {
        var poll = pollService.getPoll(pollId);
        if(poll.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var res = Optional.ofNullable(poll.get().getPollResult());
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(new PollResult(), HttpStatus.NOT_FOUND);
    }
}
