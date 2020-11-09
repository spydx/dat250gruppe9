package no.hvl.dat250.gruppe9.feedapp.restapi.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.PollResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RabbitSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    public void broadcast(String message) {
        logger.info("Sending message {}", message);
        this.rabbitTemplate.convertAndSend(message);
    }

    public void publishNewPoll(Poll p) {
        var mapper = new ObjectMapper();
        try {
            var json = mapper.writeValueAsBytes(p);
            this.rabbitTemplate.convertAndSend("FEEDAPP_NEWPOLL", json);
        } catch (IOException e) {
            logger.error("Failed to create JSON for open poll");
        }
    }

    public void publishClosedPoll(Poll p) {
        var mapper = new ObjectMapper();
        try {
            var json = mapper.writeValueAsBytes(p);
            this.rabbitTemplate.convertAndSend("FEEDAPP_CLOSED", json);
        } catch (IOException e) {
            logger.error("Failed to create json for closed poll");
        }
    }

    public void publishResult(PollResult result) {
        var mapper = new ObjectMapper();
        try {
            var json = mapper.writeValueAsBytes(result);
            this.rabbitTemplate.convertAndSend("FEEDAPP_RESULT",json);
        } catch (IOException e) {
            logger.error("failed to create json for result");
            logger.error("JSONFAILED: {}", e);
        }
    }
}
