package org.travelers.statistics.service.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.Country;

import static org.mockito.Mockito.*;
import static org.travelers.statistics.web.rest.TestUtil.convertObjectToJson;
import static org.travelers.statistics.web.rest.TestUtil.getCountry;


@SpringBootTest(classes = StatisticsApp.class)
class CountryMapperTest {

    @InjectMocks
    private CountryMapper mapper;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void mapToCountry() throws JsonProcessingException {
        Country country = getCountry();

        String json = convertObjectToJson(country);

        doReturn(country).when(objectMapper).readValue(json, Country.class);

        mapper.mapToCountry(json);

        verify(objectMapper).readValue(json, Country.class);
        verifyNoMoreInteractions(objectMapper);
    }

    @Test
    void mapToJson() throws JsonProcessingException {
        Country country = getCountry();
        String json = convertObjectToJson(country);

        doReturn(json).when(objectMapper).writeValueAsString(country);

        mapper.mapToJson(country);

        verify(objectMapper).writeValueAsString(country);
        verifyNoMoreInteractions(objectMapper);
    }
}
