package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskgroupDao;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
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
public class TaskgroupDaoImpl implements TaskgroupDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public List<Taskgroup> getTaskgroupsByWorkspaceID(int workspaceID) {
        try {
            String sql = "SELECT * FROM taskgroup WHERE workspace_id = ?";
            return template.query(sql, new BeanPropertyRowMapper<Taskgroup>(Taskgroup.class), workspaceID);
        } catch (Exception e) {
            log.error("Dao: Taskgroup: fail to fetch taskgroups where workspaceID={}: {}", workspaceID, e.getMessage());
            return new ArrayList<Taskgroup>();
        }
    }

    @Override
    public int insertTaskgroup(String taskgroupName, int workspaceID) {
        try {
            String sql = "INSERT INTO taskgroup(name, workspace_id)" +
                    "VALUES(?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, taskgroupName);
                    ps.setObject(2, workspaceID);
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (DataAccessException e) {
            log.error("Dao: insert taskgroup failed: taskgroupName: {}, workspaceID: {} : {}",
                    taskgroupName, workspaceID, e.getMessage());
            return -1;
        }
    }
}
