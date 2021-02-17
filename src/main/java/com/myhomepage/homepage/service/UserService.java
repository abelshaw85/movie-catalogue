package com.myhomepage.homepage.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.myhomepage.homepage.dto.UserDTO;
import com.myhomepage.homepage.entity.User;

public interface UserService extends UserDetailsService {
	public User findByUserName(String userName);
	public void save(UserDTO userDto);
}
