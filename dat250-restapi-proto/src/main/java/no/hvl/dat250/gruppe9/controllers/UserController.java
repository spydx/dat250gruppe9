package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.entities.FeedPoll;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //TODO: Returns uncorrectly formated JSON.
    @GetMapping("/")
    public List<FeedUser> allUsers() {
        return userService.getAll();
    }

    @GetMapping(value = "/{userId}")
    public FeedUser getUserById(@PathVariable("userId") final Long id) {
        return userService.getUser(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/polls/{userId}")
    public Set<FeedPoll> allPollsFromUser(@PathVariable("userId") final Long id) {
        return userService.getAllPolls(id);
    }

    @PostMapping(value = "/")
    public FeedUser createUser(@RequestBody FeedUser newUser) {
        return userService.addUser(newUser);
    }

    //200 (OK). 404 (Not Found), if ID not found or invalid.
    @DeleteMapping(value = "/{userId}")
    public FeedUser deleteUser(@PathVariable("userId") final Long id) {
        return userService.deleteUser(id);
    }

    //200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.
    @PutMapping(value = "/{userId}")
    public FeedUser updateUser(@PathVariable("userId") final Long userid,
                             @RequestBody FeedUser updatedUser) {
        var foundUser = userService.getUser(userid);

        if(foundUser.getEmail().equals(updatedUser.getEmail())) {
            return userService.updateUser(updatedUser);

        }
        return null;
    }
}
