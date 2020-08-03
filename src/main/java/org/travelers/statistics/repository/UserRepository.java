package org.travelers.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.travelers.statistics.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByLogin(String login);

    @Query("{ date: {$gte: ?0, $lte: ?1}}")
    List<User> findUsersByDateBetween(LocalDate startDate, LocalDate endDate);

}
