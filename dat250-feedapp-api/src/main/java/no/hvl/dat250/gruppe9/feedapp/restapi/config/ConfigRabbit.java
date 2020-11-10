package no.hvl.dat250.gruppe9.feedapp.restapi.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigRabbit {

    private final String FANOUT_QUEUE_NAME = "feedapp.fanout.queue";
    private final String FANOUT_EXCHANGE_NAME = "feedapp.fanout.exchange";
    private final String TOPIC_QUEUE_NAME = "feedapp.topic.queue";

    @Value("${spring.rabbitmq.template.exchange}")
    private String TOPIC_EXCHANGE_NAME;

    public static final String BINDING_PATTERN_ERROR = "#.error";
    private static final boolean NON_DURABLE = false;
    private static final boolean DO_NOT_AUTO_DELETE = false;

    @Bean
    public Declarables topicBindings() {
        var topicQueue = new Queue(TOPIC_QUEUE_NAME, NON_DURABLE);
        var topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, NON_DURABLE,
                DO_NOT_AUTO_DELETE);
        return new Declarables(topicQueue, topicExchange, BindingBuilder
                .bind(topicQueue)
                .to(topicExchange)
                .with(BINDING_PATTERN_ERROR));
    }
    @Bean
    public Declarables fanoutBindings() {
        var fanoutQueue = new Queue(FANOUT_QUEUE_NAME, NON_DURABLE);
        var fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, NON_DURABLE,
                DO_NOT_AUTO_DELETE);
        return new Declarables(fanoutQueue, fanoutExchange, BindingBuilder
                .bind(fanoutQueue)
                .to(fanoutExchange));
    }
}
