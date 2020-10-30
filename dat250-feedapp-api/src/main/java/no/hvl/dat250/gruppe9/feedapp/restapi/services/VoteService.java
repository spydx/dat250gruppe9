package no.hvl.dat250.gruppe9.feedapp.restapi.services;


import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollResultDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.VoteDOA;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.*;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceVoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteDOA voteStorage;

    @Autowired
    private UserService userService;

    @Autowired
    private PollResultDAO pollresultStorage;

    @Value("${app.anonymous.account}")
    private String anonAccount;

    public Optional<Vote> vote(String voterid, Poll poll, VoteDTO response) {
        var profile = userService.getProfileByAccount(voterid);
        var result = pollresultStorage.get(poll.getPollResult().getId());
        if(profile.isPresent() && result.isPresent()) {
            var vote = new Vote();
            var ur = response.getAnswer() ? result.get().addYes() : result.get().addNo();
            vote.setAnswer(response.getAnswer());
            vote.setVoter(profile.get().getId());
            vote.setPollresult(poll.getPollResult());
            vote.setVotetime(new Date());
            profile.get().getVotedOn().add(vote);
            return voteStorage.save(vote);

        }
        return Optional.empty();
    }

    public Optional<List<Vote>> deviceVote(IoT voter, DeviceVoteDTO responses) {
        var voteaccount = userService.getAccount(anonAccount);
        var poll = voter.getConnectedPoll();

        var l = new ArrayList<Vote>();
        if(voteaccount.isPresent()) {
            for (int i = 0; i < responses.getYes(); i++) {
                var yes = new Vote();
                yes.setAnswer(true);
                yes.setVotetime(new Date());
                yes.setVoter(voteaccount
                        .get()
                        .getProfile()
                        .getId());
                yes.setPollresult(voter
                        .getConnectedPoll()
                        .getPollResult()
                        .addYes());
                l.add(yes);
                voteStorage.save(yes);
            }

            for (int i = 0; i < responses.getNo(); i++) {
                var no = new Vote();
                no.setAnswer(false);
                no.setVotetime(new Date());
                no.setVoter(voteaccount
                        .get()
                        .getProfile()
                        .getId());
                no.setPollresult(voter
                        .getConnectedPoll()
                        .getPollResult()
                        .addNo());
                l.add(no);
                voteStorage.save(no);
            }
        }

        return Optional.of(l);
    }

    private boolean haveVoted(Vote vote) {
        var voter = userService.getProfile(vote.getVoter());
        if(voter.isPresent()) {
            var votelist = voter.get().getVotedOn();
            if(votelist.contains(vote))
                return true;
        }
        return false;
    }


    public Optional<Vote> voteAnonynmous(Poll poll, VoteDTO votedto) {
        var voteaccount = userService.getAccount(anonAccount);
        var result = pollresultStorage.get(poll.getPollResult().getId());
        if(voteaccount.isPresent() && result.isPresent()) {

            var vote = new Vote();
            vote.setAnswer(votedto.getAnswer());
            var ur = votedto.getAnswer() ? result.get().addYes() : result.get().addNo();
            vote.setVoter(voteaccount.get().getProfile().getId());
            vote.setPollresult(ur);
            vote.setVotetime(new Date());
            return voteStorage.save(vote);
        }
        return Optional.empty();
    }
}
