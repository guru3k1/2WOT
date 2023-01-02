package com.cgasystems.BCP.controller;

import com.cgasystems.BCP.aspect.Authorize;
import com.cgasystems.BCP.aspect.ControllerLogger;
import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TaskTime;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.service.TaskService;
import com.cgasystems.BCP.service.TimeUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/addTask")
    public Task addTask(@RequestBody Task task){
        if(task != null){
            return taskService.addTask(task);
        }else{
            Task badTask = new Task();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid Task to be added");
            return badTask;
        }
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/updateTask")
    public Task updateTask(@RequestBody Task task){
        if(task != null){
            return taskService.updateTask(task);
        }else{
            Task badTask = new Task();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid Task to update");
            return badTask;
        }
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/closeTask")
    public Task closeTask(@RequestBody Task task){
        if(task != null){
            return taskService.closeTask(task);
        }else{
            Task badTask = new Task();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid Task to update");
            return badTask;
        }
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/startTaskTime")
    public Task startTaskTime(@RequestBody Task task){
        if(task.getTaskId() != 0){
            return taskService.startTaskTime(task.getTaskId());
        }else{
            Task badTask = new Task();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid task id");
            return badTask;
        }
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/stopTaskTime")
    public Task stopTaskTime(@RequestBody Task task){
        if(task.getTaskId() != 0){
            return taskService.stopTaskTime(task.getTaskId());
        }else{
            Task badTask = new Task();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid task id");
            return badTask;
        }
    }

    @ControllerLogger
    @Authorize
    @PostMapping("/getTaskTime")
    public TaskTime getTaskTime(@RequestBody Task task){
        if(task.getTaskId() != 0){
            return taskService.getTaskTime(task);
        }else{
            TaskTime badTask = new TaskTime();
            badTask.setStatusOk(false);
            badTask.setMessage("Invalid task id");
            return badTask;
        }
    }
}
