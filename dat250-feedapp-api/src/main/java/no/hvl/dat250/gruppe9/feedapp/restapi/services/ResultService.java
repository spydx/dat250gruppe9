package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollResultDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.VoteDOA;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private PollResultDAO pollResultDAO;
    @Autowired
    private PollDAO pollDAO;

    @Autowired
    private VoteDOA voteStorage;

    public Optional<List<PollResult>> getAll() {
        var list = pollResultDAO.getAll();
        if(list.isPresent())
            return list;

        return Optional.empty();
    }

    public Optional<PollResult> getResult(String id) {
        return pollResultDAO.get(id);
    }

    public Optional<PollResult> generateResult(String pollid) {
        var poll = pollDAO.get(pollid);
        var votes = voteStorage.getVotesForPoll(pollid);

        if(votes.isPresent() && poll.isPresent()) {
            var pollres = new PollResult();
            pollres.setTotal(votes.get().size());
            int yes = 0;
            for(var v : votes.get()) {
                var b = v.getAnswer();
                if(Boolean.TRUE.equals(b)) {
                    yes++;
                }
            }
            var no = votes.get().size()-yes;
            pollres.setYes(yes);
            pollres.setNos(no);
            poll.get().setPollResult(pollres);
            pollDAO.update(poll.get());
            pollResultDAO.save(pollres);
            return Optional.ofNullable(pollres);
        }

        return Optional.empty();
    }

    public Optional<PollResult> deleteResult(String id) {
        var p = pollResultDAO.get(id);
        if(p.isPresent())
            return pollResultDAO.delete(p.get());
        return Optional.empty();
    }
}
