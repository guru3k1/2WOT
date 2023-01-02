package com.cgasystems.BCP.service;

import com.cgasystems.BCP.dao.TaskDao;
import com.cgasystems.BCP.dao.TimeUserDao;
import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TaskTime;
import com.cgasystems.BCP.model.TimeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskDao taskDao;

    private final TimeUserDao timeUserDao;

    public TaskService(TaskDao taskDao, TimeUserDao timeUserDao) {
        this.taskDao = taskDao;
        this.timeUserDao = timeUserDao;
    }

    public Task getTaskById(Long taskId){
        return taskDao.getTaskById(taskId);
    }
    public Task addTask(Task task) {
        Integer taskId = taskDao.addTask(task);
        if(taskId != 0){
            task.setStatusOk(true);
            task.setMessage("Task added successfully");
            task.setTaskId(taskId);
            return task;
        }else{
            Task addedTask = new Task();
            task.setStatusOk(false);
            task.setMessage("Error adding task");
            return addedTask;
        }
    }

    public Task updateTask(Task task) {
        if(task.getTaskId()!=0){
            logger.info("Updating task with id {}",task.getTaskId());
            Task currentTask = getTaskById(task.getTaskId());
            if(!task.equalsUpdate(currentTask)){
                taskDao.updateTask(task);
                task.setMessage("Task updated successfully");
            }else{
                logger.info("No difference found in Task with current one");
                task.setMessage("No difference found in Task");
                task.setStatusOk(false);
            }
        }
        return task;
    }

    @Transactional
    public Task startTaskTime(Long taskId){
        if(taskId!=0){
            logger.info("Setting new start time for Task");
            deactivateCurrentTaskAndCloseTimeLog();
            taskDao.activateNewTask(taskId);
            taskDao.startTaskTime(taskId);
        }
        return getTaskById(taskId);
    }

    private void deactivateCurrentTaskAndCloseTimeLog(){
        try{
            Long taskId = taskDao.getActiveTask();
            taskDao.closeCurrentTaskTime(taskId);
            taskDao.deactivateCurrentTask(taskId);
        }catch(EmptyResultDataAccessException e){
            logger.error("Error deactivating current task",e);
        }

    }

    public Task stopTaskTime(long taskId) {
        deactivateCurrentTaskAndCloseTimeLog();
        return getTaskById(taskId);
    }

    public Task closeTask(Task task) {
        taskDao.closeTask(task);
        return task;
    }

    public TaskTime getTaskTime(Task task){
        TaskTime taskTime = new TaskTime();
        Set<TimeLog> logSet= timeUserDao.getTimeLogsByTaskId(task.getTaskId());
        logSet.forEach(timeLog -> {
            LocalDate localDate = LocalDate.of(timeLog.getStartTime().getYear(),timeLog.getStartTime().getMonthValue(),timeLog.getStartTime().getDayOfMonth());
            if(!taskTime.getDailyTimeMap().containsKey(localDate)){
                Set<TimeLog> dateLogSet= new HashSet<>();
                dateLogSet.add(timeLog);
                taskTime.getDailyTimeMap().put(localDate,dateLogSet);
                taskTime.getDurationMap().put(localDate,timeLog.getDuration());
            }else{
                Set<TimeLog> dateLogSet= taskTime.getDailyTimeMap().get(localDate);
                Duration previousDuration = taskTime.getDurationMap().get(localDate);
                Duration currentDuration = previousDuration.plus(timeLog.getDuration());
                dateLogSet.add(timeLog);
                taskTime.getDailyTimeMap().put(localDate,dateLogSet);
                taskTime.getDurationMap().put(localDate,currentDuration);
            }
        });
        return taskTime;
    }
}
