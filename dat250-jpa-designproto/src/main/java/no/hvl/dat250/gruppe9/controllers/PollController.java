package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
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
    private FeedUserDAO feedUserDAO = new FeedUserDAO();

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    //TODO: Returns incorrect JSON due to looping in relations.
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
        return pollService.getPollOwner(id);
    }

    @PostMapping(value = "/{pollId}") //TODO: how are we doing this??
    public FeedPoll createPoll(@RequestBody FeedPoll newPoll, Long ownerId) {
        newPoll.setOwner(feedUserDAO.getUser(ownerId));
        return pollService.addPoll(newPoll);
    }

    @DeleteMapping(value = "/{pollId}")
    public FeedPoll deletePoll(@PathVariable("pollId") final Long id) {
        return pollService.deletePoll(id);
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