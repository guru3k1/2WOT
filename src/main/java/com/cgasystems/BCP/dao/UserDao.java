package com.cgasystems.BCP.dao;

import com.cgasystems.BCP.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao extends CommonDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    String getUser = "SELECT * FROM USERS WHERE ID = :id";
    public User getUserById(Long id) {
        User user;
        try{
            user = namedParameterJdbcTemplate.queryForObject(getUser, Map.of("id",id), userManualRowMapper()) ;
        }catch (EmptyResultDataAccessException exception){
          logger.error("Error getting user for id "+id,exception);
          user = new User();
          user.setStatusOk(false);
          user.setMessage("Error getting user - User do not exists ");
        }
        return user;
    }

    private RowMapper<User> userManualRowMapper() {
        return (rs, rownum) ->{
            User bean = new User();
            bean.setFirstName(rs.getString("FIRST_NAME"));
            bean.setId(rs.getInt("ID"));
            bean.setLastName(rs.getString("LAST_NAME"));
            bean.setUserId(rs.getString("USER_ID"));
            return bean;
        };
    }
}

//@ExceptionHandler
