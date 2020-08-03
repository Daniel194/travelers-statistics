package org.travelers.statistics.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.repository.UserRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = {"ADMIN"})
@SpringBootTest(classes = StatisticsApp.class)
class StatisticsResourceTestIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MockMvc restUserMockMvc;

    @Test
    void getUserStatistics() throws Exception {
        User user1 = new User();
        user1.setDate(LocalDate.now());
        user1.setLogin("1");

        User user2 = new User();
        user2.setDate(LocalDate.now());
        user2.setLogin("2");

        User user3 = new User();
        user3.setDate(LocalDate.now().minusDays(1));
        user3.setLogin("3");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        restUserMockMvc.perform(get("/api/statistic/user?begin=" + start + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getCountryStatistics() throws Exception {
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

        restUserMockMvc.perform(get("/api/statistic/country?begin=" + start + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(2));
    }


    @Test
    void getCountryStatisticsByCountry() throws Exception {
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

        restUserMockMvc.perform(get("/api/statistic/country/111?begin=" + start + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(2));
    }
}
