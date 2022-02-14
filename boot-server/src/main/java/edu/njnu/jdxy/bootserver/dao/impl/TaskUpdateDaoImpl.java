package edu.njnu.jdxy.bootserver.dao.impl;


import edu.njnu.jdxy.bootserver.dao.TaskUpdateDao;
import edu.njnu.jdxy.bootserver.pojo.TaskUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TaskUpdateDaoImpl implements TaskUpdateDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public List<TaskUpdate> getTaskUpdatesByTaskID(int taskID) {
        List<TaskUpdate> updates = new ArrayList<TaskUpdate>();
        try {
            String sql = "SELECT * FROM task_update WHERE task_id = ?";
            updates = template.query(sql, new BeanPropertyRowMapper<TaskUpdate>(TaskUpdate.class), taskID);
        } catch (DataAccessException e) {
            log.error("Dao: task update failed: taskID: {}: {}", taskID, e.getMessage());
        } finally {
            return updates;
        }
    }

    @Override
    public int insertTaskUpdate(int taskID, int userID, String content) {
        try {
            String sql = "INSERT INTO task_update(content, task_id, user_id) VALUES(?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, content);
                    ps.setObject(2, taskID);
                    ps.setObject(3, userID);
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            log.error("Dao: insert update failed: taskID:{}, userID:{}: {}", taskID, userID, content);
            return -1;
        }
    }
}
