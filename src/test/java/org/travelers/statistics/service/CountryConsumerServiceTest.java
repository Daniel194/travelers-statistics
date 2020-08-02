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
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.mapper.CountryMapper;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.travelers.statistics.web.rest.TestUtil.convertObjectToJson;
import static org.travelers.statistics.web.rest.TestUtil.getCountry;

@SpringBootTest(classes = StatisticsApp.class)
class CountryConsumerServiceTest {
    private static KafkaContainer kafkaContainer;

    @InjectMocks
    private CountryConsumerService countryConsumerService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private KafkaProperties kafkaProperties;

    @BeforeAll
    static void startServer() {
        kafkaContainer = new KafkaContainer("5.4.0");
        kafkaContainer.start();
    }

    @Test
    void consumeAddCountry() throws JsonProcessingException {
        Country country = getCountry();

        String json = convertObjectToJson(country);

        doReturn(getConsumerProps()).when(kafkaProperties).getConsumerProps();
        doReturn(country).when(countryMapper).mapToCountry(any(String.class));

        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProps());
        producer.send(new ProducerRecord<>("add-country", json));

        countryConsumerService.setUp();
        countryConsumerService.consumeAddCountry();

        verify(kafkaProperties, atLeast(1)).getConsumerProps();
        verify(countryMapper).mapToCountry(any(String.class));
        verify(countryRepository).save(country);
        verifyNoMoreInteractions(kafkaProperties, countryMapper, countryRepository);
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
