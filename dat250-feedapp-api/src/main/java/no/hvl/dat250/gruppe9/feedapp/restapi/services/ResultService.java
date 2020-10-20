package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollResultDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    private final PollResultDAO pollResultDAO;

    @Autowired
    public ResultService(PollResultDAO pollResultDAO) {
        this.pollResultDAO = pollResultDAO;
    }

    public Optional<List<PollResult>> getAll() {
        var list = pollResultDAO.getAll();
        if(list.isPresent())
            return list;

        return Optional.empty();
    }

    public Optional<PollResult> getResult(Long id) {
        return pollResultDAO.get(id);
    }

    public Optional<PollResult> deleteResult(Long id) {
        var p = pollResultDAO.get(id);
        if(p.isPresent())
            return pollResultDAO.delete(p.get());
        return Optional.empty();
    }
}
