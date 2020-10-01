package no.hvl.dat250.gruppe9.restservice;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PollController {
    FeedPollDAO pollDAO = new FeedPollDAO();

    @GetMapping("/polls")
    public List<FeedPoll> polls() {
        return pollDAO.getAll();
    }

    @GetMapping("/user/polls")
    public String polls(@RequestParam(value = "id") int id) { return "Under development"; }

}