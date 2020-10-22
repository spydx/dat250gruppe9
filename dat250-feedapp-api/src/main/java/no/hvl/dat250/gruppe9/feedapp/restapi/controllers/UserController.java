package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAll() {

        var res = userService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Profile> getUserById(@PathVariable("userId") final String id) {
        var res = userService.getProfile(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Account> createUser(@RequestBody Account newUser) {
        var res = userService.add(newUser);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(newUser, HttpStatus.NO_CONTENT);
    }

    //200 (OK). 404 (Not Found), if ID not found or invalid.
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Account> deleteUser(@PathVariable("userId") final String id) {
        var found = userService.getProfile(id);
        if(found.isPresent()) {
            var res = userService.delete(found.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable("userId") final String userid,
                                              @RequestBody Profile updatedUser) {
        var foundUser = userService.getProfile(userid);
        if(foundUser.isPresent()) {
            updatedUser.setId(foundUser.get().getId());
            var res = userService.update(updatedUser);
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}




