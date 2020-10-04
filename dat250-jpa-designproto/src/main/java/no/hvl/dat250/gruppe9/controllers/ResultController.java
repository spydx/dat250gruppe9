package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
import no.hvl.dat250.gruppe9.entities.FeedPollResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {
    FeedPollResultDAO resultDAO = new FeedPollResultDAO();

    @GetMapping("/result")
    public FeedPollResult result(@RequestParam(value = "result_id") int resultId) {
        return resultDAO.getResult(resultId);
    }


}