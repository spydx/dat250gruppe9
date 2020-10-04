package no.hvl.dat250.gruppe9.controllers;

import java.util.List;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.entities.FeedVotes;
import no.hvl.dat250.gruppe9.services.PollService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/")
    public List<FeedPoll> polls() {
        return pollService.getAll();
    }

    @GetMapping(value = "/{pollid}")
    public FeedPoll pollById(@PathVariable("pollid") final Long id)
    {
        return pollService.getById(id);
    }

    @GetMapping(value = "/{pollid}/owner")
    public FeedUser getOwner(@PathVariable("pollid") final Long id) {
        return pollService.getPollOwner(id);
    }

    @PostMapping(value = "/{pollId}")
    public String createPoll(@PathVariable("pollId") final Long id) {
        return "created poll id" + id;
    }

    @DeleteMapping(value = "/{pollId}")
    public String deletePoll(@PathVariable("pollId") final Long id) {
        return "DELETED poll id" + id;
    }

    @PutMapping(value = "/{pollId}")
    public String updatePoll(@PathVariable("pollId") final Long id) {
        return "Update poll id" + id;
    }
/*
    @GetMapping("/userPolls")
    public List<FeedPoll> userPolls(@RequestParam(value = "user_id") int userId) {
        return userDAO.getPollList(userId);
    }

    @GetMapping("/userVotedOn")
    public List<FeedVotes> userVotedOn(@RequestParam(value = "user_id") int userId) {
        return userDAO.getVotedOnList(userId);
    }*/

}