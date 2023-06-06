package com.yog.electronicstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yog.electronicstore.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keywords);


}



