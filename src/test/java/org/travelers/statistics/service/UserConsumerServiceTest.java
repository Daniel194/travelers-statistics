package org.travelers.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.repository.search.UserSearchRepository;
import org.travelers.statistics.service.mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.travelers.statistics.web.rest.TestUtil.convertObjectToJson;
import static org.travelers.statistics.web.rest.TestUtil.getUser;

@SpringBootTest(classes = StatisticsApp.class)
class UserConsumerServiceTest {
    private static KafkaContainer kafkaContainer;

    @InjectMocks
    private UserConsumerService userConsumerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private KafkaProperties kafkaProperties;


    @BeforeAll
    static void startServer() {
        kafkaContainer = new KafkaContainer("5.4.0");
        kafkaContainer.start();
    }


    @Test
    void consumeCreateNewUser() throws JsonProcessingException {
        User user = getUser();

        String userJson = convertObjectToJson(user);

        doReturn(getConsumerProps()).when(kafkaProperties).getConsumerProps();
        doReturn(user).when(userMapper).mapToUser(any(String.class));

        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProps());
        producer.send(new ProducerRecord<>("create-new-user", userJson));

        userConsumerService.setUp();
        userConsumerService.consumeCreateNewUser();

        verify(kafkaProperties, atLeast(1)).getConsumerProps();
        verify(userMapper).mapToUser(any(String.class));
        verify(userRepository).save(user);
        verifyNoMoreInteractions(kafkaProperties, userMapper, userRepository);
    }

    private Map<String, Object> getProducerProps() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("bootstrap.servers", kafkaContainer.getBootstrapServers());
        return producerProps;
    }

    private Map<String, Object> getConsumerProps() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("bootstrap.servers", kafkaContainer.getBootstrapServers());
        consumerProps.put("auto.offset.reset", "earliest");

        return consumerProps;
    }

}
