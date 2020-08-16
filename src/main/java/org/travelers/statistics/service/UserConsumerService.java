package org.travelers.statistics.service;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.mapper.UserMapper;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Service
public class UserConsumerService {

    private final Logger log = LoggerFactory.getLogger(UserConsumerService.class);

    private final UserRepository userRepository;
    private final KafkaProperties kafkaProperties;
    private final UserMapper userMapper;

    private KafkaConsumer<String, String> createNewUser;

    @Autowired
    public UserConsumerService(UserRepository userRepository,
                               KafkaProperties kafkaProperties,
                               UserMapper userMapper) {
        this.userRepository = userRepository;
        this.kafkaProperties = kafkaProperties;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void setUp() {
        Map<String, Object> createNewUserProps = kafkaProperties.getConsumerProps();
        createNewUserProps.put(ConsumerConfig.GROUP_ID_CONFIG, "create-new-user-statistics");

        createNewUser = new KafkaConsumer<>(createNewUserProps);
        createNewUser.subscribe(Collections.singleton("create-new-user"));
    }

    public void consumeCreateNewUser() {
        createNewUser.poll(Duration.ofSeconds(1)).forEach(record -> createNewUser(record.value()));
    }

    private void createNewUser(String value) {
        try {
            User user = userMapper.mapToUser(value);

            user.setDate(LocalDate.now());

            userRepository.save(user);

        } catch (Exception ex) {
            log.trace("ERROR create-new-user: {}", ex.getMessage(), ex);
        }
    }

}
