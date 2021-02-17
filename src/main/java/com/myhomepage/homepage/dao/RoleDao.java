package com.myhomepage.homepage.dao;

import com.myhomepage.homepage.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
