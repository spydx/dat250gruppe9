package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.ProfileDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JwtTokenProvider tokenProvider;

    //TODO: to be removed, just for prototyping
    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAll() {

        var res = userService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{profileid}")
    public ResponseEntity<Profile> getUserById(@PathVariable("profileid") final String id) {
        var res = userService.getProfile(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //TODO: Secure with access controll only for logged in an admins.
    //TODO: admins can delete any, user can only delete them selfs.
    @DeleteMapping(value = "/{profileid}")
    public ResponseEntity<Account> deleteUser(@PathVariable("profileid") final String id) {
        var found = userService.getProfile(id);
        if(found.isPresent()) {
            var res = userService.delete(found.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{profileid}")
    public ResponseEntity<Profile> updateProfile(@RequestHeader("Authorization")  final String token,
                                            @PathVariable("profileid") final String profileid,
                                            @RequestBody ProfileDTO updatedUser) {
        var authuser = tokenProvider.parseHeader(token);
        var profile = userService.getProfile(profileid);
        if(authuser.isPresent() && profile.isPresent()) {
            if(authuser.get().equals(profile.get().getAccount().getId())) {
                profile.get().setFirstname(updatedUser.getFirstname());
                profile.get().setLastname(updatedUser.getLastname());
                var res = userService.update(profile.get());
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }


    @GetMapping(value ="/{profileId}/votes/")
    public ResponseEntity<?> listUserVotes (
            @RequestHeader("Authorization")  final String token,
            @PathVariable("profileid") String profileid,
            @RequestBody VoteDTO response) {

        var authuser = tokenProvider.parseHeader(token);
        var profile = userService.getProfile(profileid);
        return ResponseEntity.ok("sorryryyyyyy");
    }
}





