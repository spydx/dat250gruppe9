package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollDAO pollStorage;

    @Autowired
    public PollService(PollDAO polldata) {
        this.pollStorage = polldata;
    }

    //Authenticated
    public Optional<List<Poll>> getAll() {
        return pollStorage.getAll();
    }

    //Get all non Authenticated
    public Optional<List<Poll>> getAllPublic() {
        var list = pollStorage.getAll();
        var result = new ArrayList<Poll>();
        for(var p : list.get()) {
            if(p.getAccess() == Access.PUBLIC)
                result.add(p);
        }
        return Optional.ofNullable(result);
    }


    public Optional<Poll> getPoll(Long id) {
        return pollStorage.get(id);
    }


    public Optional<Poll> addPoll(Poll newpoll) {
        return pollStorage.save(newpoll);
    }

    public Optional<Poll> deletePoll(Long id) {
        var found = pollStorage.get(id);
        if(found.isPresent())
            return pollStorage.delete(found.get());
        return found;
    }
}
