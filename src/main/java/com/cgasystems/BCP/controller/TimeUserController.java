package com.cgasystems.BCP.controller;

import com.cgasystems.BCP.aspect.Authorize;
import com.cgasystems.BCP.aspect.ControllerLogger;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.model.User;
import com.cgasystems.BCP.service.TimeUserService;
import com.cgasystems.BCP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/timeuser")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class TimeUserController {

    private static final Logger logger = LoggerFactory.getLogger(TimeUserController.class);
    private TimeUserService timeUserService;

    public TimeUserController(TimeUserService timeUserService) {
        this.timeUserService = timeUserService;
    }

    @ControllerLogger
    @Authorize
    @GetMapping("/getUser/{id}")
    public TimeUser getUserById(@PathVariable(value = "id") Long id){
        if(id != null){
            return timeUserService.getUserById(id);
        }else{
            TimeUser badUser = new TimeUser();
            badUser.setStatusOk(false);
            badUser.setMessage("Invalid user id");
            return badUser;
        }
    }
}
