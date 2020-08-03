package org.travelers.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.travelers.statistics.web.rest.TestUtil.convertObjectToJson;
import static org.travelers.statistics.web.rest.TestUtil.getUser;

@SpringBootTest(classes = StatisticsApp.class)
public class UserConsumerServiceTestIT {

    private static KafkaContainer kafkaContainer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private UserConsumerService userConsumerService;

    @BeforeAll
    static void startServer() {
        kafkaContainer = new KafkaContainer("5.4.0");
        kafkaContainer.start();
    }

    @BeforeEach
    void setup() {
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setProducer(getProducerProps());
        kafkaProperties.setConsumer(getConsumerProps());

        userConsumerService = new UserConsumerService(userRepository, kafkaProperties, userMapper);
        userConsumerService.setUp();
    }

    @Test
    public void consumeCreateNewUser() throws JsonProcessingException {
        User user = getUser();

        KafkaProducer<String, String> producer = new KafkaProducer<>(new HashMap<>(getProducerProps()));
        producer.send(new ProducerRecord<>("create-new-user", convertObjectToJson(user)));

        userConsumerService.consumeCreateNewUser();

        List<User> result = userRepository.findByLogin(user.getLogin());

        assertThat(result).isNotNull();
        assertThat(result.size()).isGreaterThan(0);
    }

    private Map<String, String> getProducerProps() {
        Map<String, String> producerProps = new HashMap<>();
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("bootstrap.servers", kafkaContainer.getBootstrapServers());
        return producerProps;
    }

    private Map<String, String> getConsumerProps() {
        Map<String, String> consumerProps = new HashMap<>();
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("bootstrap.servers", kafkaContainer.getBootstrapServers());
        consumerProps.put("auto.offset.reset", "earliest");

        return consumerProps;
    }
}
