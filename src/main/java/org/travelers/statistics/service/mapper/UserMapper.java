package org.travelers.statistics.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelers.statistics.domain.User;

@Service
public class UserMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public User mapToUser(String user) throws JsonProcessingException {
        return objectMapper.readValue(user, User.class);
    }

    public String mapToJson(User user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

}
