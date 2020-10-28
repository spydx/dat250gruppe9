package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.RoleDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.reponse.InternalServerError;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.RoleEnum;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ProfileDAO profileStorage;
    @Autowired
    private AccountDAO accountStorage;
    @Autowired
    private RoleDAO roleStorage;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<List<Profile>> getAll() {
        var list =  profileStorage.getAll();
        if(list.isPresent())
            return list;

        return Optional.empty();
    }

    public Optional<Profile> getProfileByAccount(String accountid) {
        return profileStorage.getByAccount(accountid);
    }
    public Optional<Profile> getProfile(String profileid) {
        return profileStorage.get(profileid);
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

    public Optional<Account> promoteToAdmin(Account toadmin) {
        var admrole = roleStorage.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(
                        () -> new InternalServerError("Cannot promote to user {} to admin")
                        );
        var rolelist = toadmin.getRoles();
        rolelist.add(admrole);
        toadmin.setRoles(rolelist);
        var res = accountStorage.update(toadmin);
        return res;
    }

    public boolean validateAdmin(String accountid) {
        var account = accountStorage.get(accountid);
        if(account.isPresent()) {
            var role = roleStorage.findByName(RoleEnum.ROLE_ADMIN).get();
            var userroles = account.get().getRoles();
            return userroles.contains(role);
        }
        return false;
    }


    public Optional<Account> add(AccountDTO newaccount) {
        var account = new Account(newaccount.getEmail());
        var role = roleStorage.findByName(RoleEnum.ROLE_USER).orElseThrow(
                () -> new InternalServerError("User Role not set")
        );
        account.setRoles(Collections.singleton(role));
        account.setPassword(passwordEncoder.encode(newaccount.getPassword()));
        var profile = new Profile(newaccount.getFirstname(), newaccount.getLastname());

        account.setProfile(profile);
        var acc = accountStorage.save(account);
        var pro = acc.get().getProfile();
        pro.setAccount(acc.get());
        profileStorage.save(pro);
        return acc;

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
                account.setPassword(passwordEncoder.encode(updated.getPassword()));
                return accountStorage.update(account);
            }
            logger.error("Cant find profile for {}", updated );
        }
        return Optional.empty();
    }

}
