package no.hvl.dat250.gruppe9.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/result")
public class ResultController {

    @GetMapping("/{pollId}")
    public String getResult(@RequestParam(value = "pollId") Long pollId) {
        return "found the reust for " + pollId;
    }

    @DeleteMapping("/{pollId}")
    public String deleteResult(@RequestParam(value = "pollId") Long pollId) {
        return "deleted the result for " + pollId;
    }
}