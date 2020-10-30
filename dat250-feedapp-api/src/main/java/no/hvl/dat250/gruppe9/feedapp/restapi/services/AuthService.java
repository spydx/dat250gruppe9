package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.IoTDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.AccountPrincipals;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.DevicePrincipals;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private  AccountDAO accountStorage;
    @Autowired
    private IoTDAO deviceStorage;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var account = accountStorage.getByEmail(username);
        if(account.isPresent())
            return AccountPrincipals.create(account.get());
        var device = deviceStorage.getByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Device not found with id: "+ username));
        return DevicePrincipals.create(device);
    }

    public Optional<UserDetails> loadUserById(String id) {
        var account = accountStorage.get(id);

        if(account.isPresent())
            return Optional.ofNullable(AccountPrincipals.create(account.get()));

        var device = deviceStorage.get(id)
                .orElseThrow(() -> new UsernameNotFoundException("Device not found with id: "+ id));
        return Optional.ofNullable(DevicePrincipals.create(device));
    }
}
