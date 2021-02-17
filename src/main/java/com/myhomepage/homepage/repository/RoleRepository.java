package com.myhomepage.homepage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myhomepage.homepage.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}
