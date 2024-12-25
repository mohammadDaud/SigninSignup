package com.mc.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.myapp.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

}
