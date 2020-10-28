package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
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

    //TODO: Change to deduce the user from token
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Account> updateAccount(@PathVariable("userId") final String userid,
                                                 @RequestBody Account updatedAccount) {
        var foundUser = userService.updateAccount(userid, updatedAccount);
        if(foundUser.isPresent()) {
            return new ResponseEntity<>(foundUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
