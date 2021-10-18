package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Option<User> findByEmail(String email);
}
