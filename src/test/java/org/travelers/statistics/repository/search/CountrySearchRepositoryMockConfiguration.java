package org.travelers.statistics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Configure a Mock version of {@link CountrySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
class CountrySearchRepositoryMockConfiguration {

    @MockBean
    private CountrySearchRepository mockCountrySearchRepository;

}
