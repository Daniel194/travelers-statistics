package org.travelers.statistics.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.User;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.travelers.statistics.web.rest.TestUtil.*;

@SpringBootTest(classes = StatisticsApp.class)
public class UserMapperTestIT {

    @Autowired
    private UserMapper userMapper;

    @Test
    void mapToPost() throws IOException {
        User user = getUser();
        String json = convertObjectToJson(user);

        User result = userMapper.mapToUser(json);

        assertThat(result.getLogin()).isEqualTo(result.getLogin());
    }

    @Test
    void mapToJson() throws IOException {
        User user = getUser();

        String json = userMapper.mapToJson(user);

        assertThat(json).isNotNull();
        assertThat(json).isNotEmpty();
        assertThat(json).isNotBlank();
    }

}
