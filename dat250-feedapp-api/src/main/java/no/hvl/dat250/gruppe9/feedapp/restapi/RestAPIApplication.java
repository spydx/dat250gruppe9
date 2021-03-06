package no.hvl.dat250.gruppe9.feedapp.restapi;

import no.hvl.dat250.gruppe9.feedapp.restapi.messaging.RabbitSender;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.SetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
@EntityScan( basePackages = {"no.hvl.dat250.gruppe9.feedapp"} )
public class RestAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestAPIApplication.class, args);
	}

    @Component
    public class ApplicationStartupRunner implements CommandLineRunner {
        protected final Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private SetupService setupService;

        @Autowired
        private RabbitSender sender;

        @Override
        public void run(String... args) throws Exception {
            setupService.init();
            sender.broadcast("test");
            logger.info("ApplicationStartupRunner run method Started !!");
        }
    }
}