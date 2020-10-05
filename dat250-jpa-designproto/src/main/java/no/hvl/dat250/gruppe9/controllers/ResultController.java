package no.hvl.dat250.gruppe9.controllers;


import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.services.PollService;
import no.hvl.dat250.gruppe9.services.ResultService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/result")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService =resultService;
    }

    @GetMapping(value = "/{pollId}")
    public FeedPollResult getResult(@PathVariable(value = "pollId") final Long id) {
        return resultService.getResult(id);
    }

    @DeleteMapping(value = "/{pollId}") //TODO: is this necessary. Could be done if a poll gets deleted
    public String deleteResult(@PathVariable(value = "pollId") final Long id) {
        return "deleted the result for " + id;
    }
}