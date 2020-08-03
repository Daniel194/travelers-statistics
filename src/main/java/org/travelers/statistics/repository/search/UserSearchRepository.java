package org.travelers.statistics.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.User;

@Repository
public interface UserSearchRepository extends ElasticsearchRepository<User, String> {
}
