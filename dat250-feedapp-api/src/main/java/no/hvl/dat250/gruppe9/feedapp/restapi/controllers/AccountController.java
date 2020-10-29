package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PasswordDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PutMapping(value = "/{accountid}")
    public ResponseEntity<Account> updateAccount(
                                    @RequestHeader("Authorization") final String token,
                                    @PathVariable("accountid") final String accountid,
                                    @RequestBody PasswordDTO updatedAccount) {

        var accid = tokenProvider.parseHeader(token);
        if(accid.isPresent()) {
            var profile = userService.getProfileByAccount(accid.get());
            var admin = userService.validateAdmin(accid.get());
            if(profile.isPresent() || admin) {
                var foundUser = userService.updateAccount(profile.get().getId(), updatedAccount);
                if(foundUser.isPresent()) {
                    return new ResponseEntity<>(foundUser.get(), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
