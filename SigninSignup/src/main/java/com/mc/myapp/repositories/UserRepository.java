package com.mc.myapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.myapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findByFirstNameOrEmail(String firstName, String email);

	Optional<User> findByEmailAndToken(String email, String token);
}
