package no.hvl.dat250.gruppe9.controllers;


import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.services.PollService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/result")
public class ResultController {

    private final PollService pollService;

    public ResultController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/{pollId}")
    public FeedPollResult getResult(@PathVariable(value = "pollId") final Long id) {
        return pollService.getResult(id);
    }

    @DeleteMapping("/{pollId}")
    public String deleteResult(@PathVariable(value = "pollId") final Long id) {
        return "deleted the result for " + id;
    }
}