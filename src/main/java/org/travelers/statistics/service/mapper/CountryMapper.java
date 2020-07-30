package org.travelers.statistics.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelers.statistics.domain.Country;

@Service
public class CountryMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public CountryMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Country mapToCountry(String country) throws JsonProcessingException {
        return objectMapper.readValue(country, Country.class);
    }

    public String mapToJson(Country country) throws JsonProcessingException {
        return objectMapper.writeValueAsString(country);
    }

}
