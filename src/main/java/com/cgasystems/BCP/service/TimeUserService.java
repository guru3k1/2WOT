package com.cgasystems.BCP.service;

import com.cgasystems.BCP.dao.TimeUserDao;
import com.cgasystems.BCP.dao.UserDao;
import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TimeUserService {

    private TimeUserDao timeUserDao;
    public TimeUserService(TimeUserDao timeUserDao) {
        this.timeUserDao = timeUserDao;
    }

    public TimeUser getUserById(long id) {
        return timeUserDao.getUserById(id);
    }
}
