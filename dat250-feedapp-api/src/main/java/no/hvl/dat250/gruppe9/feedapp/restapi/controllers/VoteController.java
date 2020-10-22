package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Missing implementation
@RestController
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Vote>> getVoteForUser(@PathVariable("userId") final String id) {
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/{pollId}")
    public ResponseEntity<Vote> voteOnPoll() {
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
