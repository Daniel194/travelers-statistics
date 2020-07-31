package org.travelers.statistics.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.travelers.statistics.StatisticsApp;
import org.travelers.statistics.domain.User;

import static org.mockito.Mockito.*;
import static org.travelers.statistics.web.rest.TestUtil.*;

@SpringBootTest(classes = StatisticsApp.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private ObjectMapper objectMapper;


    @Test
    void mapToUser() throws JsonProcessingException {
        User user = getUser();

        String json = convertObjectToJson(user);

        doReturn(user).when(objectMapper).readValue(json, User.class);

        userMapper.mapToUser(json);

        verify(objectMapper).readValue(json, User.class);
        verifyNoMoreInteractions(objectMapper);
    }

    @Test
    void mapToJson() throws JsonProcessingException {
        User user = getUser();
        String json = convertObjectToJson(user);

        doReturn(json).when(objectMapper).writeValueAsString(user);

        userMapper.mapToJson(user);

        verify(objectMapper).writeValueAsString(user);
        verifyNoMoreInteractions(objectMapper);
    }
}
