package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.services.PollService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/") //TODO: this returns the owners password!! fix it
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
        return pollService.getPollOwner(id);
    }

    @PostMapping(value = "/{pollId}") //TODO: how are we doing this??
    public String createPoll(@PathVariable("pollId") String yes, String no, String name, String question) {
        return "Created poll";
    }

    @DeleteMapping(value = "/{pollId}")
    public FeedPoll deletePoll(@PathVariable("pollId") final Long id) {
        return pollService.deletePoll(id);
    }

    @PutMapping(value = "/{pollId}") //TODO: should this be possible?? brings unwanted consequences
    public String updatePoll(@PathVariable("pollId") final Long id) {
        return "Update poll id" + id;
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