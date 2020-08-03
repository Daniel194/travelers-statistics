package org.travelers.statistics.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StatisticsApp.class)
public class UserServiceTestIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void getStatistics() {
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

        List<UserDTO> results = userService.getStatistics(start, end);

        assertThat(results.size()).isEqualTo(2);
    }

}
