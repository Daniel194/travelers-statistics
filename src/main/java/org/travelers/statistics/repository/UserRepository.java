package org.travelers.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByLogin(String login);

}
