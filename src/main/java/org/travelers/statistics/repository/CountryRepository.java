package org.travelers.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.Country;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {

    List<Country> findByLoginAndCountry(String login, String country);

    @Query("{ date: {$gte: ?0, $lte: ?1}}")
    List<Country> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("{$and: [{ date: {$gte: ?0, $lte: ?1}}, {country: ?2}]}")
    List<Country> findByDateBetweenAndCountry(LocalDate startDate, LocalDate endDate, String country);

}
