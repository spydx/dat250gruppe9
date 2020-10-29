package no.hvl.dat250.gruppe9.feedapp.restapi.services;


import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.VoteDOA;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
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

    @Value("${app.anonymous.account}")
    private String anonAccount;

    public Optional<Vote> vote(String voterid, Poll poll, VoteDTO response) {
        var profile = userService.getProfileByAccount(voterid);
        if(!haveVoted(poll, profile.get())) {
            var vote = new Vote();
            vote.setAnswer(response.getAnswer());
            vote.setVoter(profile.get().getId());
            vote.setPoll(poll);
            vote.setVotetime(new Date());
            profile.get().getVotedOn().add(vote);
            return voteStorage.save(vote);
        }
        return Optional.empty();
    }

    //TODO : needs to be rewritten
    public Optional<List<Vote>> deviceVote(IoT voter, Poll poll, List<VoteDTO> responses) {
        int count = 1;
        var l = new ArrayList<Vote>();
        for (var a : responses) {
            var d = new Date();
            var vote = new Vote();
            vote.setAnswer(a.getAnswer());
            vote.setVoter(voter.getId()+"-" + count++);
            vote.setPoll(poll);
            vote.setVotetime(d);
            var res = voteStorage.save(vote);
            l.add(res.get());
        }
        return Optional.of(l);
    }

    private boolean haveVoted(Poll poll, Profile user) {
        var reslist = voteStorage.getVoteUserPoll(poll.getId());
        if(reslist.isPresent()) {
            var votelist = reslist.get();
            var v = new Vote();
            v.setPoll(poll);
            v.setVoter(user.getId());
            return votelist.contains(v);
        }
        return false;
    }


    public Optional<Vote> voteAnonynmous(Poll poll, VoteDTO votedto) {
        var voteaccount = userService.getAccount(anonAccount);
        if(voteaccount.isPresent()) {
            var vote = new Vote();
            vote.setAnswer(votedto.getAnswer());
            vote.setVoter(voteaccount.get().getProfile().getId());
            vote.setPoll(poll);
            vote.setVotetime(new Date());
            return voteStorage.save(vote);
        }
        return Optional.empty();
    }
}
