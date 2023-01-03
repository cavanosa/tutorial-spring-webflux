package com.tutorial.tutorialweblux.security.repository;

import com.tutorial.tutorialweblux.security.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByUsernameOrEmail(String username, String email);
}
