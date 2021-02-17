package com.myhomepage.homepage.dao;

import com.myhomepage.homepage.entity.User;

public interface UserDao {
    public User findByUserName(String userName);
    public void save(User user);
}
