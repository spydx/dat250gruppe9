package no.hvl.dat250.gruppe9.feedapp.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan( basePackages = {"no.hvl.dat250.gruppe9.feedapp"} )
public class RestAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestAPIApplication.class, args);
	}
}