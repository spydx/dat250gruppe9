package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;


import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.ResultService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/")
    public ResponseEntity<List<PollResult>> getAllResult() {
        var res = resultService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(),HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{resultid}")
    public ResponseEntity<PollResult> getResult(@PathVariable(value = "resultid") final String id) {
        var res =  resultService.getResult(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //TODO: is this necessary. Could be done if a poll gets deleted
    //TODO: needs authentication
    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<PollResult> deleteResult(
            @RequestHeader("Authorization") final String token,
            @PathVariable(value = "pollId") final String id) {
        var userid = tokenProvider.parseHeader(token);
        var access = userService.validateAdmin(userid.get());
        if(userid.isPresent() && access) {
            var res = resultService.deleteResult(id);
            if (res.isPresent()) {
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
}