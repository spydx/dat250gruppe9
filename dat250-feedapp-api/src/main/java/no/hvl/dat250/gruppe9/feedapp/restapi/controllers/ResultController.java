package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;


import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.ResultService;
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

    @GetMapping("/")
    public ResponseEntity<List<PollResult>> getAllResult() {
        var res = resultService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(),HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{pollId}")
    public ResponseEntity<PollResult> getResult(@PathVariable(value = "pollId") final String id) {
        var res =  resultService.getResult(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //TODO: is this necessary. Could be done if a poll gets deleted
    //TODO: needs authentication
    @DeleteMapping(value = "/{pollId}")
    public ResponseEntity<PollResult> deleteResult(@PathVariable(value = "pollId") final String id) {
        var res = resultService.deleteResult(id);
        if (res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{pollId}")
    public ResponseEntity<PollResult> generateResult(@PathVariable(value = "pollId") final String id) {
        var res = resultService.generateResult(id);
        if (res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
}