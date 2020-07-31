package org.travelers.statistics.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.Country;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.travelers.statistics.web.rest.TestUtil.convertObjectToJson;
import static org.travelers.statistics.web.rest.TestUtil.getCountry;

@SpringBootTest(classes = StatisticsApp.class)
public class CountryMapperTestIT {

    @Autowired
    private CountryMapper countryMapper;

    @Test
    void mapToPost() throws IOException {
        Country country = getCountry();
        String json = convertObjectToJson(country);

        Country result = countryMapper.mapToCountry(json);

        assertThat(result.getLogin()).isEqualTo(result.getLogin());
    }

    @Test
    void mapToJson() throws IOException {
        Country country = getCountry();

        String json = countryMapper.mapToJson(country);

        assertThat(json).isNotNull();
        assertThat(json).isNotEmpty();
        assertThat(json).isNotBlank();
    }

}
