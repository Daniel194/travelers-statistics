package org.travelers.statistics.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.dto.CountryDTO;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StatisticsApp.class)
public class CountryServiceTestIT {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    @Test
    void getStatistics() {
        Country country1 = new Country();
        country1.setCountry("111");
        country1.setLogin("1");
        country1.setDate(LocalDate.now());

        Country country2 = new Country();
        country2.setCountry("111");
        country2.setLogin("1");
        country2.setDate(LocalDate.now());

        Country country3 = new Country();
        country3.setCountry("222");
        country3.setLogin("2");
        country3.setDate(LocalDate.now());

        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        List<CountryDTO> results = countryService.getStatistics(start, end);

        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    void getStatisticsByCountry() {
        Country country1 = new Country();
        country1.setCountry("111");
        country1.setLogin("1");
        country1.setDate(LocalDate.now());

        Country country2 = new Country();
        country2.setCountry("111");
        country2.setLogin("1");
        country2.setDate(LocalDate.now());

        Country country3 = new Country();
        country3.setCountry("111");
        country3.setLogin("2");
        country3.setDate(LocalDate.now().minusDays(1));

        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        List<CountryDTO> results = countryService.getStatistics(start, end, "111");

        assertThat(results.size()).isEqualTo(2);
    }
}
