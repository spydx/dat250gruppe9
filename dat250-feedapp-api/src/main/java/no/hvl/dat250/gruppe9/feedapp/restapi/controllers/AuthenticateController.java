package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authenticate")
public class Authenticate {
    private final AuthService authService;

    @Autowired
    public Authenticate(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> authenticate(@RequestBody Account user) {
        var res = authService.authenticate(user);
        if(res)
            return new ResponseEntity<>("Auth: OK" , HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
