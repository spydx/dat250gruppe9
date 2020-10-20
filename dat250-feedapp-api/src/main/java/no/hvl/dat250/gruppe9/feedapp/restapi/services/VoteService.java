package no.hvl.dat250.gruppe9.feedapp.restapi.services;


import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.VoteDOA;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.AccountData;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteDOA voteStorage;

    @Autowired
    public VoteService(VoteDOA voteStorage) {
        this.voteStorage = voteStorage;
    }

    public Optional<Vote> vote(AccountData voter, Poll poll) {

        return Optional.empty();
    }
}
