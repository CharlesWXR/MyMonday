package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskDependencyDao;
import edu.njnu.jdxy.bootserver.pojo.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TaskDependencyDaoImpl implements TaskDependencyDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Task> getDependenciesByDestTaskID(int taskID) {
        List<Task> tasks = new ArrayList<Task>();
        try {
            String sql = "SELECT from_id FROM task_dependency WHERE dest_id = ?";
            List<Integer> ids = template.queryForList(sql, Integer.class, taskID);

            String queryForTasks = "SELECT * FROM task WHERE id = ?";
            for (int id : ids) {
                tasks.add(template.queryForObject(queryForTasks, new BeanPropertyRowMapper<Task>(Task.class), id));
            }
        } catch (DataAccessException e) {
            log.error("Dao: get dependencies failed: taskID: {} : {}", taskID, e.getMessage());
        } finally {
            return tasks;
        }
    }

    @Override
    public List<Task> getDependenciesByFromTaskID(int taskID) {
        List<Task> tasks = new ArrayList<Task>();
        try {
            String sql = "SELECT dest_id FROM task_dependency WHERE from_id = ?";
            List<Integer> ids = template.queryForList(sql, Integer.class, taskID);

            String queryForTasks = "SELECT * FROM task WHERE id = ?";
            for (int id : ids) {
                tasks.add(template.queryForObject(queryForTasks, new BeanPropertyRowMapper<Task>(Task.class), id));
            }
        } catch (Exception e) {
            log.error("Dao: get dependencies failed: taskID: {}: {}", taskID, e.getMessage());
        } finally {
            return tasks;
        }
    }

    @Override
    public boolean deleteByDestTaskID(int taskID) {
        try {
            String sql = "DELETE FROM task_dependency WHERE dest_id = ?";
            return template.update(sql, taskID) >= 0;
        } catch (DataAccessException e) {
            log.error("Dao: failed to delete dependencies: dest taskID: {}: {}", taskID, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertTaskDependencies(List<Integer> fromTaskIDs, int destTaskID) {
        try {
            String sql = "INSERT INTO task_dependency(from_id, dest_id)" +
                    "VALUES(?, ?)";
            int[] res = template.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setObject(1, fromTaskIDs.get(i));
                    preparedStatement.setObject(2, destTaskID);
                }

                @Override
                public int getBatchSize() {
                    return fromTaskIDs.size();
                }
            });

            for (int i : res)
                if (i <= 0 && i != -2)
                    return false;

            return true;
        } catch (DataAccessException e) {
            log.error("Dao: failed to insert dependencies: fromIDs: {}, toIDs:{} : {}",
                    fromTaskIDs.toString(), destTaskID, e.getMessage());
            return false;
        }
    }
}
