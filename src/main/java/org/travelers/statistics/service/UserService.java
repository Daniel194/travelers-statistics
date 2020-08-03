package org.travelers.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getStatistics(LocalDate startDate, LocalDate endDate) {
        List<User> users = userRepository.findUsersByDateBetween(startDate, endDate);
        Map<LocalDate, List<User>> groups = users.stream().collect(Collectors.groupingBy(User::getDate));

        List<UserDTO> statistics = new ArrayList<>();

        for (Map.Entry<LocalDate, List<User>> group : groups.entrySet()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setDate(group.getKey());
            userDTO.setCount(group.getValue().size());

            statistics.add(userDTO);
        }

        return statistics;
    }

}
