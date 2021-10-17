package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
