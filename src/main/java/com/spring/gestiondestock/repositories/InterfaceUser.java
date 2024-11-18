package com.spring.gestiondestock.repositories;

import com.spring.gestiondestock.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InterfaceUser extends JpaRepository<Users, Integer> {
   Optional<Users> findByUsername(String username);

}
