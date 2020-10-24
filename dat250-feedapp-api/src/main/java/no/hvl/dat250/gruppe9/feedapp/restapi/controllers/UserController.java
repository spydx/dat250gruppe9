package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import jdk.javadoc.doclet.Reporter;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.FeedAPIResponse;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private PollService pollService;

    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAll() {

        var res = userService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Profile> getUserById(@PathVariable("userId") final String id) {
        var res = userService.getProfile(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Account> deleteUser(@PathVariable("userId") final String id) {
        var found = userService.getProfile(id);
        if(found.isPresent()) {
            var res = userService.delete(found.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable("userId") final String userid,
                                              @RequestBody Profile updatedUser) {
        var foundUser = userService.getProfile(userid);
        if(foundUser.isPresent()) {
            updatedUser.setId(foundUser.get().getId());
            var res = userService.update(updatedUser);
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //TODO: Remove this? Use Poll voting instead.
    @PostMapping(value ="/{userId}/vote/{pollId}")
    public ResponseEntity<Vote> voteOnPoll(
            @PathVariable("userId") String userId,
            @PathVariable("pollId") String pollId,
            @RequestBody VoteDTO response) {
        var profile = userService.getProfile(userId);
        var poll = pollService.getPoll(pollId);

        if (profile.isPresent() && poll.isPresent()) {
            var res = voteService.vote(profile.get(), poll.get(), response);
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}





