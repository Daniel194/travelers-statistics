package org.travelers.statistics.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.mapper.CountryMapper;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Service
public class CountryConsumerService {

    private final Logger log = LoggerFactory.getLogger(CountryConsumerService.class);

    private final CountryRepository countryRepository;
    private final KafkaProperties kafkaProperties;
    private final CountryMapper countryMapper;

    private KafkaConsumer<String, String> addCountry;

    public CountryConsumerService(CountryRepository countryRepository,
                                  KafkaProperties kafkaProperties,
                                  CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.kafkaProperties = kafkaProperties;
        this.countryMapper = countryMapper;
    }

    @PostConstruct
    public void setUp() {
        Map<String, Object> createNewUserProps = kafkaProperties.getConsumerProps();
        createNewUserProps.put(ConsumerConfig.GROUP_ID_CONFIG, "add-country-statistics");

        addCountry = new KafkaConsumer<>(createNewUserProps);
        addCountry.subscribe(Collections.singleton("add-country"));
    }

    public void consumeAddCountry() {
        addCountry.poll(Duration.ofSeconds(1)).forEach(record -> addCountry(record.value()));
    }

    private void addCountry(String value) {
        try {
            Country country = countryMapper.mapToCountry(value);

            country.setDate(LocalDate.now());

            countryRepository.save(country);

        } catch (Exception ex) {
            log.trace("ERROR add-country: {}", ex.getMessage(), ex);
        }
    }

}
