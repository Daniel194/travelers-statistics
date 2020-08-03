package org.travelers.statistics.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.dto.CountryDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = StatisticsApp.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    void getStatistics() {
        Country country1 = new Country();
        country1.setCountry("1");

        Country country2 = new Country();
        country2.setCountry("1");

        Country country3 = new Country();
        country3.setCountry("2");

        List<Country> countries = Arrays.asList(country1, country2, country3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        when(countryRepository.findByDateBetween(start, end))
            .thenReturn(countries);

        List<CountryDTO> results = countryService.getStatistics(start, end);

        assertThat(results.size()).isEqualTo(2);

        verify(countryRepository).findByDateBetween(start, end);
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void getStatisticsByCountry() {
        Country country1 = new Country();
        country1.setCountry("1");
        country1.setDate(LocalDate.now());

        Country country2 = new Country();
        country2.setCountry("1");
        country2.setDate(LocalDate.now());

        Country country3 = new Country();
        country3.setCountry("2");
        country3.setDate(LocalDate.now().minusDays(1));

        List<Country> countries = Arrays.asList(country1, country2, country3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        when(countryRepository.findByDateBetweenAndCountry(start, end, "TST"))
            .thenReturn(countries);

        List<CountryDTO> results = countryService.getStatistics(start, end, "TST");

        assertThat(results.size()).isEqualTo(2);

        verify(countryRepository).findByDateBetweenAndCountry(start, end, "TST");
        verifyNoMoreInteractions(countryRepository);
    }

}
