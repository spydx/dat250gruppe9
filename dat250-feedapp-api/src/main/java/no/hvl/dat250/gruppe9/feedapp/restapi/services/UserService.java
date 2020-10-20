package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.UserAccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.AccountData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserAccountDAO userStorage;

    @Autowired
    public UserService(UserAccountDAO usermgnt) {
        this.userStorage = usermgnt;
    }

    public Optional<List<AccountData>> getAll() {
        var list =  userStorage.getAll();
        if(list.isPresent())
            return list;

        return Optional.empty();
    }

    public Optional<AccountData> getUser(Long id) {
        return userStorage.get(id);
    }

    public Optional<AccountData> delete(AccountData user) {
        var u = userStorage.get(user.getId());
        if(u.isPresent())
            return userStorage.delete(u.get());

        return Optional.empty();
    }
    public Optional<AccountData> add(AccountData accountData) {
        return userStorage.save(accountData);
    }

    public Optional<AccountData> update(AccountData accountData) {
        return userStorage.update(accountData);
    }

}
