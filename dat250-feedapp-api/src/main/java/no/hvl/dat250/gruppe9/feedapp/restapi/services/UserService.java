package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final ProfileDAO profileStorage;
    private final AccountDAO accountStorage;


    public UserService(ProfileDAO profileStorage,
                       AccountDAO accountStorage) {
        this.profileStorage = profileStorage;
        this.accountStorage = accountStorage;
    }

    private String hashPassword(String password) {
        return HashingService.createPasswordHash(password);
    }


    public Optional<List<Profile>> getAll() {
        var list =  profileStorage.getAll();
        if(list.isPresent())
            return list;

        return Optional.empty();
    }

    public Optional<Profile> getProfile(String id) {
        return profileStorage.get(id);
    }

    public Optional<Account> getAccount(String email) {
        return accountStorage.getByEmail(email);
    }

    public Optional<Account> delete(Profile user) {
        var profile = profileStorage.get(user.getId());
        var account = Optional.ofNullable(profile.get().getAccount());
        if(account.isPresent()) {
            return accountStorage.delete(account.get());
        }

        return Optional.empty();
    }

    public Optional<Account> add(Account account) {
        var email = account.getEmail();
        var exist = accountStorage.getByEmail(email);
        if(exist.isEmpty()){
            var password = account.getPassword();
            account.setPassword(hashPassword(password));
            var acc = accountStorage.save(account);
            var pro = acc.get().getProfile();
            pro.setAccount(acc.get());
            profileStorage.save(pro);
            return acc;
        }
        return Optional.empty();
    }

    public Optional<Profile> update(Profile profile) {
        return profileStorage.update(profile);
    }

    //You can only update your password
    public Optional<Account> updateAccount(String id, Account updated) {
        var profile = profileStorage.get(id);
        if(profile.isPresent()) {
            var account = profile.get().getAccount();
            if(account.getEmail().equals(updated.getEmail())) {
                account.setPassword(hashPassword(updated.getPassword()));
                return accountStorage.update(account);
            }
            //TODO: logger
        }
        return Optional.empty();
    }

}
