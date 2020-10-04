package no.hvl.dat250.gruppe9.controllers;

import no.hvl.dat250.gruppe9.entities.FeedUser;
import no.hvl.dat250.gruppe9.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<FeedUser> allUsers() {
        return userService.getAll();
    }

}
