package com.cgasystems.BCP.service;

import com.cgasystems.BCP.dao.UserDao;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.model.User;
import org.springframework.stereotype.Service;

import java.sql.Time;


@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
}
