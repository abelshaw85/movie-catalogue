package com.movie.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.catalogue.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}
