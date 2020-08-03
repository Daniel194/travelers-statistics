package org.travelers.statistics.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StatisticsApp.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void getStatistics() {
        User user1 = new User();
        user1.setDate(LocalDate.now());

        User user2 = new User();
        user2.setDate(LocalDate.now());

        User user3 = new User();
        user3.setDate(LocalDate.now().minusDays(1));

        List<User> users = Arrays.asList(user1, user2, user3);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        when(userRepository.findUsersByDateBetween(start, end))
            .thenReturn(users);

        List<UserDTO> results = userService.getStatistics(start, end);

        assertThat(results.size()).isEqualTo(2);

        verify(userRepository).findUsersByDateBetween(start, end);
        verifyNoMoreInteractions(userRepository);
    }
}
