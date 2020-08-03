package org.travelers.statistics.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.travelers.statistics.service.UserService;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistic")
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private final UserService userService;

    @Autowired
    public StatisticsResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getUserStatistics(@RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
                                                           @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.debug("REST request User statistics");

        return ResponseEntity.ok(userService.getStatistics(begin, end));
    }

}
