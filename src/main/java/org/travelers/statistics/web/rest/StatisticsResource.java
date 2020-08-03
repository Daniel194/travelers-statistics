package org.travelers.statistics.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travelers.statistics.service.CountryService;
import org.travelers.statistics.service.UserService;
import org.travelers.statistics.service.dto.CountryDTO;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistic")
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private final UserService userService;
    private final CountryService countryService;

    @Autowired
    public StatisticsResource(UserService userService, CountryService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getUserStatistics(@RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
                                                           @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.debug("REST request User statistics");

        return ResponseEntity.ok(userService.getStatistics(begin, end));
    }

    @GetMapping("/country")
    public ResponseEntity<List<CountryDTO>> getCountryStatistics(@RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
                                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.debug("REST request Country statistics");

        return ResponseEntity.ok(countryService.getStatistics(begin, end));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<CountryDTO>> getCountryStatisticsByCountry(
        @PathVariable("country") String country,
        @RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        log.debug("REST request Country statistics");

        return ResponseEntity.ok(countryService.getStatistics(begin, end, country));
    }

}
