package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<FeedUser> allUsers() {
        return userService.getAll();
    }

    @GetMapping(value = "/{userId}")
    public FeedUser getUserById(@PathVariable("userid") final Long id) {
        return userService.getAll().get(1);
    }

    @PostMapping(value = "/{userId}")
    public String createPoll(@PathVariable("userId") final Long id) {
        return "created user id" + id;
    }

    @DeleteMapping(value = "/{userId}")
    public String deletePoll(@PathVariable("userId") final Long id) {
        return "DELETED user id" + id;
    }

    @PutMapping(value = "/{userId}")
    public String updatePoll(@PathVariable("userId") final Long id) {
        return "Update user id" + id;
    }



}
