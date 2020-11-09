package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollResultDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import no.hvl.dat250.gruppe9.feedapp.restapi.messaging.RabbitSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollDAO pollStorage;

    @Autowired
    private PollResultDAO pollresultStorage;

    @Autowired
    private ProfileDAO profileStorage;

    @Autowired
    private AccountDAO accountStorage;

    @Autowired
    private RabbitSender rabbitSender;

    //Authenticated
    public Optional<List<Poll>> getAllLoggedIn() {
        return pollStorage.getAllLoggedIn();
    }

    //Get all non Authenticated
    public Optional<List<Poll>> getAllPublic() {
       return  pollStorage.getAllPublic();
    }

    private Logger logger = LoggerFactory.getLogger(PollService.class);

    public Optional<Poll> getPoll(String id) {
        return pollStorage.get(id);
    }

    public Optional<Poll> getPollByName(String name) {
        return pollStorage.getPollByName(name);
    }

    public Optional<Poll> addPoll(PollDTO newpoll, String accountid) {
        var account = accountStorage.get(accountid);
        var owner = profileStorage.get(account.get().getProfile().getId());

        if(owner.isPresent()) {
            var p = new Poll();
            var r = new PollResult();
            pollresultStorage.save(r);

            var o = owner.get();

            p.setAccess(newpoll.getAccess());
            p.setQuestion(newpoll.getQuestion());
            p.setAnsweryes(newpoll.getAnsweryes());
            p.setAnswerno(newpoll.getAnswerno());
            p.setName(newpoll.getName());

            p.setTimeend(newpoll.getTimeend());
            p.setTimestart(new Date());
            p.setOwner(o);
            p.setPollResult(r);
            o.getPollList().add(p);
            var savepoll = pollStorage.save(p);
            if(savepoll.isPresent()) {
                rabbitSender.publishNewPoll(savepoll.get());
                return savepoll;
            }
        }
        return Optional.empty();
    }

    @Scheduled(fixedRate = 15000) //every 15 seconds
    public void closePolls() {
            logger.info("Closing polls");

            var query = pollStorage.getAll();
            if(query.isPresent()) {
                var list = query.get();
                for (var p : list) {
                    var end = p.getTimeend().getTime();
                    var now = new Date();
                    var before = now.getTime() - 15000;
                    if (end > before && end <= now.getTime()) {
                        logger.info("Closing {}", p.getId());
                        rabbitSender.publishClosedPoll(p);
                        var result = p.getPollResult();
                        logger.info("{}",result);
                        rabbitSender.publishResult(result);
                    }
                }
            }
    }

    public Optional<Poll> deletePoll(String id) {
        var found = pollStorage.get(id);
        if(found.isPresent())
            return pollStorage.delete(found.get());
        return found;
    }

    public Optional<List<Poll>> getUserPolls(String accountid) {
        var acc = accountStorage.get(accountid);
        if(acc.isPresent()) {
            return pollStorage.getAllUser(acc.get().getProfile().getId());
        }
        return Optional.empty();
    }
}
