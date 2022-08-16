package com.coffee.network.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coffee.network.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

  @Query("SELECT u FROM User u WHERE u.username = :username")
  Optional<User> findByUsername(
    @Param("username") String username
  );

}
