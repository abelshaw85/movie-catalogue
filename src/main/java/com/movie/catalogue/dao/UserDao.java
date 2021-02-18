package com.movie.catalogue.dao;

import com.movie.catalogue.entity.User;

public interface UserDao {
    public User findByUserName(String userName);
    public void save(User user);
}
