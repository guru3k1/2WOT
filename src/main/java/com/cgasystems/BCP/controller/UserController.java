package com.cgasystems.BCP.controller;

import com.cgasystems.BCP.aspect.Authorize;

import com.cgasystems.BCP.aspect.ControllerLogger;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.model.User;
import com.cgasystems.BCP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@ControllerLogger
    //@Authorize
    //@GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable(value = "id") Long id){
        if(id != null){
            return userService.getUserById(id);
        }else{
            User badUser = new User();
            badUser.setStatusOk(false);
            badUser.setMessage("Invalid user id");
            return badUser;
        }
    }
}
