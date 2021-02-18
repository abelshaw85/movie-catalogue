package com.movie.catalogue.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.movie.catalogue.dto.UserDTO;
import com.movie.catalogue.entity.User;

public interface UserService extends UserDetailsService {
	public User findByUserName(String userName);
	public void save(UserDTO userDto);
}
