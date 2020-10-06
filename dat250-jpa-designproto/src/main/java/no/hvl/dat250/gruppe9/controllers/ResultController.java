package no.hvl.dat250.gruppe9.controllers;


import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import no.hvl.dat250.gruppe9.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/result")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService =resultService;
    }

    @GetMapping("/")
    public List<FeedPollResult> getAllResult() {
        return resultService.getAllResult();
    }
    @GetMapping(value = "/{pollId}")
    public FeedPollResult getResult(@PathVariable(value = "pollId") final Long id) {
        return resultService.getResult(id);
    }

    @DeleteMapping(value = "/{pollId}") //TODO: is this necessary. Could be done if a poll gets deleted
    public ResponseEntity<String> deleteResult(@PathVariable(value = "pollId") final Long id) {
        if (resultService.deleteResult(id)) {
            return new ResponseEntity<String>("Ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No Content",HttpStatus.NO_CONTENT);
        }
    }
}