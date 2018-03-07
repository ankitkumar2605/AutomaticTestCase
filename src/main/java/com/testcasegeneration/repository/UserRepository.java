package com.testcasegeneration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testcasegeneration.model.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    User findByUsername(String userName);
    User findByUsernameAndEnabled(String userName,boolean isEnabled);
}