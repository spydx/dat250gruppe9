package no.hvl.dat250.gruppe9.restservice;

import java.util.List;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.entities.FeedPoll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PollController {
    FeedPollDAO pollDAO = new FeedPollDAO();
    FeedUserDAO userDAO = new FeedUserDAO();

    @GetMapping("/polls")
    public List<FeedPoll> polls() {
        return pollDAO.getAll();
    }

    @GetMapping("/userPolls")
    public List<FeedPoll> userPolls(@RequestParam(value = "user_id") int userId) {
        return userDAO.getPollList(userId);
    }

    @GetMapping("/userVotedOn")
    public List<FeedPoll> userVotedOn(@RequestParam(value = "user_id") int userId) {
        return userDAO.getVotedOnList(userId);
    }

}