package com.cgasystems.BCP.dao;

import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TimeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskDao extends CommonDao{
    private static final Logger logger = LoggerFactory.getLogger(TaskDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TaskDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    String addTask = "INSERT INTO TASK(TASK_ID,USER_ID,NAME,DETAILS,IS_COMPLETED,STATE,CURRENT_SPRINT,EXPECTED_COMPLETION_TIME, TYPE) " +
            "values (:taskId,:userId,:name,:details,false,'open',:currentSprint,:ect, :type)";
    String getNextTaskId = "select nextval('TASK_ID_SEQ')";
    //String getNextTaskIdH2 = "VALUES NEXT VALUE FOR TASK_ID_SEQ";
    //String getTaskId = "select TASK_ID_SEQ.NEXTVAL from dual";
    String addNewTimeLog="INSERT INTO TIME_LOG(TASK_ID,TIME_ID,START_TIME) VALUES (:taskId,:timeId,CURRENT_TIMESTAMP)";
    String getNextTimeLogId = "select nextval('TIME_ID_SEQ')";
    //String getNextTimeLogIdH2 = "VALUES NEXT VALUE FOR TIME_ID_SEQ";
    String getActiveTask = "SELECT TASK_ID FROM TASK WHERE STATE = 'active'";
    String closeCurrentTimeLog = "UPDATE TIME_LOG SET END_TIME = CURRENT_TIMESTAMP WHERE TASK_ID = :taskId and END_TIME is null";
    String deactivateTask = "UPDATE TASK SET STATE = 'open' WHERE TASK_ID = :taskId";
    String activateNewTask = "UPDATE TASK SET STATE = 'active' WHERE TASK_ID = :taskId";

    String getTaskById = "SELECT * from TASK WHERE TASK_ID=:taskId";

    String updateTask = "UPDATE TASK SET NAME=:name, DETAILS=:details, CURRENT_SPRINT=:currentSprint," +
            "EXPECTED_COMPLETION_TIME=:ect, TYPE=:type WHERE TASK_ID=:taskId AND USER_ID=:userId";

    String closeTask = "UPDATE TASK SET IS_COMPLETED=1 WHERE TASK_ID=:taskId AND USER_ID=:userId";

    public Integer addTask(Task task){
        int id = 0;

        try{
            Integer newTaskId =  jdbcTemplate.queryForObject(getNextTaskId, Integer.class);
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("userId",task.getUserId());
            map.addValue("taskId",newTaskId);
            map.addValue("name",task.getName());
            map.addValue("details",task.getDetails());
            map.addValue("currentSprint",task.getCurrentSprint());
            map.addValue("ect",task.getExpectedCompletionTime());
            map.addValue("type",task.getType());

            int state = namedParameterJdbcTemplate.update(addTask,map);
            if(state==1){
                return newTaskId;
            }
            throw new RuntimeException("Error in db adding task");
        }catch (Exception e){
            logger.error("Error adding task ",e);
        }
        return id;
    }

    public Integer updateTimeLog(TimeLog timeLog){
        return 0;
    }

    public void updateTask(Task task) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("userId",task.getUserId());
        map.addValue("taskId",task.getTaskId());
        map.addValue("name",task.getName());
        map.addValue("details",task.getDetails());
        map.addValue("currentSprint",task.getCurrentSprint());
        map.addValue("ect",task.getExpectedCompletionTime());
        map.addValue("type",task.getType());
        namedParameterJdbcTemplate.update(updateTask,map);
    }

    public Integer startTaskTime(Long taskId) {
        Integer newTimeLogId =  jdbcTemplate.queryForObject(getNextTimeLogId, Integer.class);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("taskId",taskId);
        map.addValue("timeId",newTimeLogId);
        int state = namedParameterJdbcTemplate.update(addNewTimeLog,map);
        if(state==1){
            return newTimeLogId;
        }
        throw new RuntimeException("Error in db adding task");
    }

    public Long getActiveTask(){
        return jdbcTemplate.queryForObject(getActiveTask, Long.class);
    }

    public void closeCurrentTaskTime(Long taskId) {
        namedParameterJdbcTemplate.update(closeCurrentTimeLog,Map.of("taskId",taskId));
    }

    public void deactivateCurrentTask(Long taskId) {
        namedParameterJdbcTemplate.update(deactivateTask,Map.of("taskId",taskId));
    }

    public void activateNewTask(Long taskId) {
        namedParameterJdbcTemplate.update(activateNewTask,Map.of("taskId",taskId));
    }

    public Task getTaskById(Long taskId) {
        Task task = namedParameterJdbcTemplate.queryForObject(getTaskById,Map.of("taskId",taskId), taskRowMapper());
        if(task != null){
            return task;
        }
        return new Task();
    }

    private RowMapper<Task> taskRowMapper() {
        return (rs, rownum) ->{
            Task taskBean = new Task();
            taskBean.setTaskId(rs.getLong("TASK_ID"));
            taskBean.setName(rs.getString("NAME"));
            taskBean.setDetails(rs.getString("DETAILS"));
            taskBean.setState(rs.getString("STATE"));
            taskBean.setUserId(rs.getInt("USER_ID"));
            taskBean.setCurrentSprint(rs.getString("CURRENT_SPRINT"));
            taskBean.setExpectedCompletionTime(rs.getString("EXPECTED_COMPLETION_TIME"));
            taskBean.setCompleted(rs.getBoolean("IS_COMPLETED"));
            taskBean.setType(rs.getString("TYPE"));
            return taskBean;
        };
    }

    public void closeTask(Task task) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("userId",task.getUserId());
        map.addValue("taskId",task.getTaskId());
        namedParameterJdbcTemplate.update(closeTask,map);
    }

//    public TimeUser getUserById(Long id) {
//        TimeUser user;
//        try{
//            user = namedParameterJdbcTemplate.queryForObject(getUser, Map.of("id",id), userManualRowMapper()) ;
//        }catch (EmptyResultDataAccessException exception){
//          logger.error("Error getting user for id "+id,exception);
//          user = new TimeUser();
//          user.setStatusOk(false);
//          user.setMessage("Error getting user - User do not exists ");
//        }
//        return user;
//    }

//    private RowMapper<TimeUser> userManualRowMapper() {
//        return (rs, rownum) ->{
//            TimeUser bean = new TimeUser();
//            bean.setFirstName(rs.getString("FIRST_NAME"));
//            bean.setEmail(rs.getString("EMAIL"));
//            bean.setLastName(rs.getString("LAST_NAME"));
//            bean.setUserId(rs.getLong("USER_ID"));
//            return bean;
//        };
//    }
}

//@ExceptionHandler
