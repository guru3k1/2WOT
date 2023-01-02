package com.cgasystems.BCP.dao;

import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TimeLog;
import com.cgasystems.BCP.model.TimeUser;
import com.cgasystems.BCP.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Repository
public class TimeUserDao extends CommonDao{
    private static final Logger logger = LoggerFactory.getLogger(TimeUserDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TimeUserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    String getTimeLogsByTaskId= "SELECT TIME_ID, TASK_ID, START_TIME, END_TIME FROM TIME_LOG WHERE TASK_ID = :taskId ORDER BY TIME_ID";

    String getUser = "SELECT TU.FIRST_NAME, TU.EMAIL,TU.LAST_NAME,TU.USER_ID," +
            "TA.TASK_ID,TA.NAME,TA.DETAILS, TA.STATE,TA.CURRENT_SPRINT, TA.EXPECTED_COMPLETION_TIME,TA.IS_COMPLETED,TA.TYPE," +
            "TL.TIME_ID, TL.TASK_ID as TIME_TASK_ID, TL.START_TIME, TL.END_TIME " +
            "FROM TIME_USER TU LEFT JOIN TASK TA on TU.USER_ID = TA.USER_ID " +
            "LEFT JOIN TIME_LOG TL on TL.TASK_ID = TA.TASK_ID WHERE TU.USER_ID = :id";
    //String getUser = "SELECT * FROM TIME_USER WHERE USER_ID = :id";
    public TimeUser getUserById(Long id) {
        TimeUser user;
        try{
            user = namedParameterJdbcTemplate.query(getUser, Map.of("id",id),rs -> {
                TimeUser timeUserBean = new TimeUser();
                timeUserBean.setUserTasks(new HashSet<>());
                Task currentTask = new Task();
                Map<Long,Task> taskMap = new HashMap<>();

                boolean firstBean = true;
                while (rs.next()){
                    timeUserBean.setFirstName(rs.getString("FIRST_NAME"));
                    timeUserBean.setEmail(rs.getString("EMAIL"));
                    timeUserBean.setLastName(rs.getString("LAST_NAME"));
                    timeUserBean.setUserId(rs.getLong("USER_ID"));
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
                    taskBean.setLoggedTime(new HashSet<>());
                    if(!taskMap.containsKey(taskBean.getTaskId()) && taskBean.getTaskId()!=0){
                        taskMap.put(taskBean.getTaskId(),taskBean);
                    }
//                    if(!currentTask.equals(taskBean)){
//                        if(currentTask.getTaskId() !=0){
//                            timeUserBean.getUserTasks().add(currentTask);
//                        }
//                        taskBean.setLoggedTime(new HashSet<>());
//                        currentTask = taskBean;
//                    }
                    if(rs.getLong("TIME_ID") != 0){
                        TimeLog timeLog = new TimeLog();
                        Long timeTaskId = rs.getLong("TIME_TASK_ID");
                        timeLog.setTimeId(rs.getLong("TIME_ID"));
                        if(rs.getTimestamp("START_TIME")!= null){
                            timeLog.setStartTime(LocalDateTime.ofInstant(rs.getTimestamp("START_TIME").toInstant(), ZoneId.systemDefault()));
                        }
                        if(rs.getTimestamp("END_TIME") != null){
                            timeLog.setEndTime(LocalDateTime.ofInstant(rs.getTimestamp("END_TIME").toInstant(),ZoneId.systemDefault()));
                        }
                        taskMap.get(timeTaskId).getLoggedTime().add(timeLog);
/*                        if(!currentTask.getLoggedTime().contains(timeLog)){
                            timeUserBean.getUserTasks().add(taskBean);
                        }
                        currentTask.getLoggedTime().add(timeLog);*/
                    }
/*                    if(timeUserBean.getUserTasks().isEmpty() && currentTask.getTaskId()!=0){
                        timeUserBean.getUserTasks().add(currentTask);
                    }*/

                }
                taskMap.forEach((k,v) -> timeUserBean.getUserTasks().add(v));
                return timeUserBean;
            }) ;
        }catch (EmptyResultDataAccessException exception){
          logger.error("Error getting user for id "+id,exception);
          user = new TimeUser();
          user.setStatusOk(false);
          user.setMessage("Error getting user - User do not exists ");
        }
        return user;
    }

    private RowMapper<TimeUser> userManualRowMapper() {
        return (rs, rownum) ->{
            TimeUser bean = new TimeUser();
            bean.setFirstName(rs.getString("FIRST_NAME"));
            bean.setEmail(rs.getString("EMAIL"));
            bean.setLastName(rs.getString("LAST_NAME"));
            bean.setUserId(rs.getLong("USER_ID"));
            return bean;
        };
    }

    public Set<TimeLog> getTimeLogsByTaskId(Long taskId){
        List<TimeLog> timeLogs;
        try{
            timeLogs =  namedParameterJdbcTemplate.query(getTimeLogsByTaskId, Map.of("taskId",taskId), timeLogMapper());
        }catch(Exception e){
            logger.error("Error getting list of TimeLog",e);
            timeLogs = new ArrayList<>();
        }
        return new HashSet<>(timeLogs);
    }

    private RowMapper<TimeLog> timeLogMapper() {
        return (rs, rownum) ->{
            TimeLog bean = new TimeLog();
            bean.setTimeId(rs.getLong("TIME_ID"));
            bean.setStartTime(LocalDateTime.ofInstant(rs.getTimestamp("START_TIME").toInstant(), ZoneId.systemDefault()));
            bean.setEndTime(LocalDateTime.ofInstant(rs.getTimestamp("END_TIME").toInstant(), ZoneId.systemDefault()));
            return bean;
        };
    }
}

//@ExceptionHandler
