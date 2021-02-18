package com.movie.catalogue.dao;

import com.movie.catalogue.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
