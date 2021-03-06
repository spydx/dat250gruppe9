package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.PollDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.ProfileDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.RoleDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.reponse.InternalServerError;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PasswordDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Value("${app.anonymous.account}")
    private String anonymousUser;

    @Autowired
    private ProfileDAO profileStorage;

    @Autowired
    private AccountDAO accountStorage;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private PollDAO pollStorage;

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
        var anon = accountStorage.getByEmail(anonymousUser);
        if(account.isPresent() && anon.isPresent()) {
            var polllist = profile.get().getPollList();
            for(var p : polllist) {
                p.setOwner(anon.get().getProfile());
                anon.get().getProfile().getPollList().add(p);
                pollStorage.save(p);
            }
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
        return accountStorage.update(toadmin);
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

    public Optional<Account> updateAccount(String profileid, PasswordDTO updated) {
        var profile = profileStorage.get(profileid);
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
