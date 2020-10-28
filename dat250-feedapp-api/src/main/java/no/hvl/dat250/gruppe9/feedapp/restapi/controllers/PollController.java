package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtControll;

    @GetMapping("/")
    public ResponseEntity<?> getAllPolls(
            @Nullable @RequestHeader("Authorization") final String token) {
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
        return new ResponseEntity("No polls found in the system ", HttpStatus.NOT_FOUND);

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

    @GetMapping(value = "/mine")
    public ResponseEntity<?> getUserPolls(@NotNull @RequestHeader("Authorization") final String token) {
        var accountid = jwtControll.parseHeader(token);
        if(accountid.isPresent()) {
            var res = pollService.getUserPolls(accountid.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/{pollid}")
    public ResponseEntity<?> pollById(@PathVariable("pollid") final String id)
    {
        var res = pollService.getPoll(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>("Poll not found " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<?> deletePoll(@NotNull @RequestHeader("Authorization") final String token,
            @PathVariable("pollId") final String pollid) {
        var accountid = jwtControll.parseHeader(token);
        if(accountid.isPresent()) {
            var profile = userService.getProfileByAccount(accountid.get());
            var poll = pollService.getPoll(pollid);
            if(profile.isPresent() && poll.isPresent()) {
                if(profile.get().getId().equals(poll.get().getOwner().getId())) {
                    var deleted = pollService.deletePoll(poll.get().getId());
                    return new ResponseEntity<>(deleted.get(),HttpStatus.OK);
                } else if(userService.validateAdmin(accountid.get())) {
                    var deleted = pollService.deletePoll(poll.get().getId());
                    return new ResponseEntity<>(deleted.get(),HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Unauthroized attempt to access",HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("No Content",HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{pollid}/owner")
    public ResponseEntity<?> getOwner(@PathVariable("pollid") final String id) {
        var poll = pollService.getPoll(id);
        if(poll.isPresent()) {
            var oid = poll.get().getOwner();
            return new ResponseEntity<>(oid, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found owner" , HttpStatus.NOT_FOUND);
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

    //Missing anonymous voting

    @PostMapping(value ="/{pollid}/vote/")
    public ResponseEntity<?> voteOnPoll(
            @Nullable @RequestHeader("Authorization") final String token,
            @PathVariable("pollid") final String pollid,
            @NotNull @RequestBody final VoteDTO votedto) {

        var accountid = jwtControll.parseHeader(token);
        var poll = pollService.getPoll(pollid);

        // anonmously voting here and we need check if poll is public
        if(accountid.isPresent() && poll.isPresent()) {
                var res = voteService.vote(accountid.get(), poll.get(), votedto);
                if(res.isPresent())
                    return new ResponseEntity<>(res.get(), HttpStatus.OK);
                return new ResponseEntity<>("Forbidden to vote twice", HttpStatus.FORBIDDEN);
        } else if (poll.isPresent() && poll.get().getAccess() == Access.PUBLIC) {
            var res = voteService.voteAnonynmous(poll.get(), votedto);
            return new ResponseEntity<>("Voted anon " + res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Not allowed to vote", HttpStatus.NO_CONTENT);
    }
}
