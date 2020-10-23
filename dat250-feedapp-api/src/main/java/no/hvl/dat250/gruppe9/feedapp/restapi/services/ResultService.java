package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollResultDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    private final PollResultDAO pollResultDAO;
    private final PollDAO pollDAO;
    @Autowired
    public ResultService(PollResultDAO pollResultDAO,
                         PollDAO pollDAO) {
        this.pollResultDAO = pollResultDAO;
        this.pollDAO = pollDAO;
    }

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
        var pollres = new PollResult();
        if(poll.isPresent()) {
            var updatedpoll = poll.get();
            var reslist = updatedpoll.getVotes();
            int yes = 0;
            for(var v : reslist) {
                Boolean b = v.getAnswer();
                if(Boolean.TRUE.equals(b))
                    yes++;
            }
            var no = reslist.size()-yes;
            pollres.setYes(yes);
            pollres.setNos(no);
            pollres.setTotal(reslist.size());
            updatedpoll.setPollResult(pollres);
            pollDAO.update(updatedpoll);
            return Optional.of(pollres);
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
