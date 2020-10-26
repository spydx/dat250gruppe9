package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
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
    private final ProfileDAO profileStorage;

    @Autowired
    public PollService(PollDAO polldata, ProfileDAO profileDAO) {
        this.pollStorage = polldata;
        this.profileStorage = profileDAO;
    }

    //Authenticated
    public Optional<List<Poll>> getAll() {
        return pollStorage.getAll();
    }

    //Get all non Authenticated
    public Optional<List<Poll>> getAllPublic() {
       return  pollStorage.getAllPublic();
    }


    public Optional<Poll> getPoll(String id) {
        return pollStorage.get(id);
    }


    public Optional<Poll> addPoll(PollDTO newpoll) {

        var owner = profileStorage.get(newpoll.getOwner());
        if(owner.isPresent()) {
            var p = new Poll();
            var o = owner.get();

            p.setAccess(newpoll.getAccess());
            p.setQuestion(newpoll.getQuestion());
            p.setAnsweryes(newpoll.getAnsweryes());
            p.setAnswerno(newpoll.getAnswerno());
            p.setName(newpoll.getName());

            p.setTimeend(newpoll.getTimeend());
            p.setTimestart(newpoll.getTimestart());
            p.setOwner(o);
            o.getPollList().add(p);
            return pollStorage.save(p);
        }
        return Optional.empty();
    }

    public Optional<Poll> deletePoll(String id) {
        var found = pollStorage.get(id);
        if(found.isPresent())
            return pollStorage.delete(found.get());
        return found;
    }
}
