package org.travelers.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.Country;

import java.util.List;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {

    List<Country> findByLoginAndCountry(String login, String country);

}
