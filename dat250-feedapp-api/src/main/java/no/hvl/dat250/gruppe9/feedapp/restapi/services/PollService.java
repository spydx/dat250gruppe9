package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollDAO pollStorage;

    @Autowired
    private ProfileDAO profileStorage;

    @Autowired
    private AccountDAO accountStorage;

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


    public Optional<Poll> addPoll(PollDTO newpoll, String accountid) {
        var account = accountStorage.get(accountid);
        var owner = profileStorage.get(account.get().getProfile().getId());

        if(owner.isPresent()) {
            var p = new Poll();
            var o = owner.get();

            p.setAccess(newpoll.getAccess());
            p.setQuestion(newpoll.getQuestion());
            p.setAnsweryes(newpoll.getAnsweryes());
            p.setAnswerno(newpoll.getAnswerno());
            p.setName(newpoll.getName());

            p.setTimeend(newpoll.getTimeend());
            p.setTimestart(new Date());
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
