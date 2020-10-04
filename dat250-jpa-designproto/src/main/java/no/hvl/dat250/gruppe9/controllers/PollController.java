package no.hvl.dat250.gruppe9.controllers;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
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

    @RequestMapping(value = "/{pollid}", method = RequestMethod.GET)
    @ResponseBody
    public FeedPoll pollById(@PathVariable("pollid") final Long id)
    {
        return pollService.getById(id);
    }
    @RequestMapping(value = "/{pollid}/owner", method = RequestMethod.GET)
    @ResponseBody
    public FeedUser getOwner(@PathVariable("pollid") final Long id) {
        return pollService.getPollOwner(id);
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