package org.travelers.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.dto.CountryDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryDTO> getStatistics(LocalDate startDate, LocalDate endDate) {
        List<Country> countries = countryRepository.findByDateBetween(startDate, endDate);
        Map<String, List<Country>> groups = countries.stream().collect(Collectors.groupingBy(Country::getCountry));

        List<CountryDTO> statistics = new ArrayList<>();

        for (Map.Entry<String, List<Country>> group : groups.entrySet()) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCountry(group.getKey());
            countryDTO.setCount(group.getValue().size());

            statistics.add(countryDTO);
        }

        return statistics;
    }

    public List<CountryDTO> getStatistics(LocalDate startDate, LocalDate endDate, String country) {
        List<Country> countries = countryRepository.findByDateBetweenAndCountry(startDate, endDate, country);
        Map<LocalDate, List<Country>> groups = countries.stream().collect(Collectors.groupingBy(Country::getDate));

        List<CountryDTO> statistics = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Country>> group : groups.entrySet()) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setDate(group.getKey());
            countryDTO.setCount(group.getValue().size());

            statistics.add(countryDTO);
        }

        return statistics;
    }

}
