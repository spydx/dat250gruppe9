package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.reponse.ResourceNotFoundException;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private JwtTokenProvider jwtControll;

    //TODO: Show ALl for PUBLIC, show all for Logged in user, take away PRIVATE.
    @GetMapping("/")
    public ResponseEntity<List<Poll>> getAllPolls(@Nullable @RequestHeader("Authorization") final String token) {
        if(token != null) {
            var accountid = jwtControll.parseHeader(token);
            if(accountid.isPresent()) {
                var res = pollService.getAllLoggedIn();
                if(res.isPresent())
                    return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }
        var res = pollService.getAllPublic();
        if (res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/")
    public ResponseEntity<Poll> createPoll(
            @NotNull @RequestHeader("Authorization") final String token,
            @RequestBody PollDTO newPoll) {

        var accountid = jwtControll.parseHeader(token);
        if(accountid.isPresent()) {

            var res = pollService.addPoll(newPoll, accountid.get());
            if (res.isPresent()) {
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getUserPolls(@NotNull @RequestHeader("Authorization") final String token) {
        var accountid = jwtControll.parseHeader(token);
        if(accountid.isPresent()) {
            var res = pollService.getUserPolls(accountid.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/{pollid}")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<Poll> pollById(@PathVariable("pollid") final String id)
    {
        var res = pollService.getPoll(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<Poll> deletePoll(@PathVariable("pollId") final String id) {
        var deleted = pollService.deletePoll(id);
        if(deleted.isPresent())
            return new ResponseEntity<>(deleted.get(),HttpStatus.OK);

        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{pollid}/owner")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<Profile> getOwner(@PathVariable("pollid") final String id) {
        var poll = pollService.getPoll(id);
        if(poll.isPresent()) {
            var oid = poll.get().getOwner();
            return new ResponseEntity<>(oid, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{pollId}/result")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
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

    @PostMapping(value ="/{pollid}/vote/")
    public ResponseEntity<?> voteOnPoll(@PathVariable("pollid") final String pollid) {
        return ResponseEntity.ok("not implemented");
    }

}
