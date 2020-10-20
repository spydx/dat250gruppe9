package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.AccountData;
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
    public ResponseEntity<List<AccountData>> getAll() {

        var res = userService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<AccountData> getUserById(@PathVariable("userId") final Long id) {
        var res = userService.getUser(id);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<AccountData> createUser(@RequestBody AccountData newUser) {
        var res = userService.add(newUser);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //200 (OK). 404 (Not Found), if ID not found or invalid.
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<AccountData> deleteUser(@PathVariable("userId") final Long id) {
        var found = userService.getUser(id);
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
    public ResponseEntity<AccountData> updateUser(@PathVariable("userId") final Long userid,
                                  @RequestBody AccountData updatedUser) {
        var foundUser = userService.getUser(userid);
        if(foundUser.isPresent()) {
            // This compares the login ID (email)
            if (foundUser.get().getAccount().equals(updatedUser.getAccount())) {
                var res = userService.update(updatedUser);
                if(res.isPresent())
                    return new ResponseEntity<>(res.get(), HttpStatus.OK);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}




