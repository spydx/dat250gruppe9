package no.hvl.dat250.gruppe9.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/error")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such content")
public class ErrorController extends RuntimeException{
}
