package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AccountDAO accountStorage;

    @Autowired
    public AuthService(AccountDAO accountStorage) {
        this.accountStorage = accountStorage;
    }

    public boolean authenticate(Account account) {
        var found = accountStorage.getByEmail(account.getEmail());
        if(found.isPresent()) {
            return HashingService.checkPassword(account.getPassword(), found.get().getPassword());
        }
        return false;
    }
}
