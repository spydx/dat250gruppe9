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

    @GetMapping(value = "/{profileid}")
    public ResponseEntity<Profile> getUserById(
            @RequestHeader("Authorization") final String token,
            @PathVariable("profileid") final String profileid) {
        var accountid = tokenProvider.parseHeader(token);
        if(accountid.isPresent()) {
            var profile = userService.getProfileByAccount(accountid.get());
            var admin = userService.validateAdmin(accountid.get());
            if(profile.isPresent()) {
                if (!profile.get().getId().equals(profileid) && !admin) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
                var res = userService.getProfile(profileid);
                if(res.isPresent())
                    return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{profileid}")
    public ResponseEntity<Account> deleteUser(@RequestHeader("Authorization") final String token,
                                              @PathVariable("profileid") final String id) {
        var accountid = tokenProvider.parseHeader(token);
        if(accountid.isPresent()) {
            var profile = userService.getProfileByAccount(accountid.get()).get();
            if(userService.validateAdmin(accountid.get()) || profile.getId().equals(id)) {
                var found = userService.getProfile(id);
                if(found.isPresent()) {
                    var res = userService.delete(found.get());
                    if(res.isEmpty()) { // this is what we expect
                        return new ResponseEntity<>(null, HttpStatus.OK);
                    }
                    return new ResponseEntity<>(res.get(), HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
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
}





