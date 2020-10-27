package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.FeedAPIResponse;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtAutheticationResponse;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.LoginDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.AuthService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("api/auth")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticate(@NotNull @Valid @RequestBody LoginDTO login) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new JwtAutheticationResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@NotNull @Valid @RequestBody AccountDTO newUser) {
        var exist = userService.getAccount(newUser.getEmail());

        if(exist.isPresent()) {
            return new ResponseEntity(new FeedAPIResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        var res = userService.add(newUser);
        if(res.isPresent()) {

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(res.get().getProfile().getId()).toUri();
            return ResponseEntity.created(location).body(new FeedAPIResponse(true, "Registred"));
        }
        return new ResponseEntity<>(newUser, HttpStatus.NO_CONTENT);
    }
}
