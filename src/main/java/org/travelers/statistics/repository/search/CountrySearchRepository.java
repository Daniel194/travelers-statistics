package org.travelers.statistics.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.Country;

@Repository
public interface CountrySearchRepository extends ElasticsearchRepository<Country, String> {

}
