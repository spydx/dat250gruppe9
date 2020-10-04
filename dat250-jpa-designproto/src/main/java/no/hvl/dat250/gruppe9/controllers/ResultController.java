package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("result")
public class ResultController {

    @GetMapping("/{pollId}")
    public String getResult(@RequestParam(value = "pollId") Long pollId) {
        return "found the reust for " + pollId;
    }

    @DeleteMapping("/{pollId}")
    public String deleteResult(@RequestParam(value = "pollId") Long pollId) {
        return "deleted the reust for " + pollId;
    }


}