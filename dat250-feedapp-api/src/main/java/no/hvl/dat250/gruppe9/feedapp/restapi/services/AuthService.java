package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.AccountPrincipals;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final AccountDAO accountStorage;

    @Autowired
    public AuthService(AccountDAO accountStorage) {
        this.accountStorage = accountStorage;
    }

    public boolean authenticate(LoginDTO account) {
        var found = accountStorage.getByEmail(account.getEmail());
        if(found.isPresent()) {
            return HashingService.checkPassword(account.getPassword(), found.get().getPassword());
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var account = accountStorage.getByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email));
        return AccountPrincipals.create(account);
    }

    public UserDetails loadUserById(String id) {
        var account = accountStorage.get(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        return AccountPrincipals.create(account);
    }
}
