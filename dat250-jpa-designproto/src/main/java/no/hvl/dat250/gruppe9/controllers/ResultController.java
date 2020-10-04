package no.hvl.dat250.gruppe9.controllers;


import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.services.PollService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/result")
public class ResultController {

    private PollService pollService;

    public ResultController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/{pollId}")
    public FeedPollResult getResult(@RequestParam(value = "pollId") Long pollId) {
        return pollService.getResult(pollId);
    }

    @DeleteMapping("/{pollId}")
    public String deleteResult(@RequestParam(value = "pollId") Long pollId) {
        return "deleted the result for " + pollId;
    }
}