package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskDao;
import edu.njnu.jdxy.bootserver.dao.UserDao;
import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
public class TaskDaoImpl implements TaskDao {
    @Autowired
    JdbcTemplate template;

    @Autowired
    UserDao userDao;

    @Override
    public List<Task> getTasksByTaskGroupID(int id) {
        try {
            String sql = "SELECT * FROM task WHERE taskgroup_id = ?";
            return template.query(sql, new BeanPropertyRowMapper<Task>(Task.class), id);
        } catch (DataAccessException e) {
            log.error("Dao: Task: fail to fetch tasks with Task Group ID = {}: {}", id, e.getMessage());
            return new ArrayList<Task>();
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> res = new ArrayList<Task>();
        try {
            String sql = "SELECT * FROM task WHERE id != 0";
            res = template.query(sql, new BeanPropertyRowMapper<Task>(Task.class));
        } catch (DataAccessException e) {
            log.error("Dao: fail to get all tasks");
        } finally {
            return res;
        }
    }

    @Override
    public List<MiniUser> getInitiatorsByTaskID(int id) {
        List<MiniUser> miniUsers = new ArrayList<MiniUser>();

        try {
            String sql = "SELECT initiator_id FROM task_initiator WHERE task_id = ?";
            List<Integer> userIDs = template.queryForList(sql, Integer.class, id);
            for (int i : userIDs)
                miniUsers.add(userDao.queryForMiniUserBuID(i));
            return miniUsers;
        } catch (Exception e) {
            log.error("Dao: Fail to fetch initiators in task_id={}: {}", id, e.getMessage());
            return miniUsers;
        }
    }

    @Override
    public List<MiniUser> getAccepterByTaskID(int id) {
        List<MiniUser> miniUsers = new ArrayList<MiniUser>();

        try {
            String sql = "SELECT accepter_id FROM task_accepter WHERE task_id = ?";
            List<Integer> userIDs = template.queryForList(sql, Integer.class, id);
            for (int i : userIDs)
                miniUsers.add(userDao.queryForMiniUserBuID(i));
            return miniUsers;
        } catch (Exception e) {
            log.error("Dao: Fail to fetch initiators in task_id={}: {}", id, e.getMessage());
            return miniUsers;
        }
    }

    @Override
    public boolean updateTask(int target, String attr, Object newVal) {
        log.info(target + " " + attr + " " + newVal);
        try {
            String sql = "UPDATE task SET " + attr + " = ? WHERE id = ?";
            return template.update(sql, newVal, target) > 0;
        } catch (DataAccessException e) {
            log.error("Dao: Fail to update task with id={}: {}", target, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTaskInitiator(int taskID) {
        try {
            String sql = "DELETE FROM task_initiator WHERE task_id = ?";
            return template.update(sql, taskID) > 0;
        } catch (DataAccessException e) {
            log.error("Dao: Task: fail to delete initiator in task:{}: {}", taskID, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateTaskInitiator(int targetID, List<Integer> initiatorsIDs) {
        try {
            String sql = "INSERT INTO task_initiator(task_id, initiator_id) VALUES(?, ?)";
            int[] res = template.batchUpdate(sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setObject(1, targetID);
                            preparedStatement.setObject(2, initiatorsIDs.get(i));
                        }

                        @Override
                        public int getBatchSize() {
                            return initiatorsIDs.size();
                        }
                    });

            for (int i : res)
                if (i != -2 && i <= 0)
                    return false;

            return true;
        } catch (DataAccessException e) {
            log.error("Dao: Task: failed to add initiators to taskID:{}: {}", targetID, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTaskAccepter(int taskID) {
        try {
            String sql = "DELETE FROM task_accepter WHERE task_id = ?";
            return template.update(sql, taskID) > 0;
        } catch (Exception e) {
            log.error("Dao: delete task accepter: taskID:{}: {}", taskID, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateTaskAccepter(int taskID, List<Integer> accepterIDs) {
        try {
            String sql = "INSERT INTO task_accepter(task_id, accepter_id) VALUES(?, ?)";
            int[] res = template.batchUpdate(sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setObject(1, taskID);
                            preparedStatement.setObject(2, accepterIDs.get(i));
                        }

                        @Override
                        public int getBatchSize() {
                            return accepterIDs.size();
                        }
                    });

            for (int i : res)
                if (i != -2 && i <= 0)
                    return false;

            return true;
        } catch (DataAccessException e) {
            log.error("Dao: update task accepter: taskID:{} : {}", taskID, e.getMessage());
            return false;
        }
    }

    @Override
    public int insertTask(Task task) {
        try {
            String sql = "INSERT INTO task(name, description, ddl, type, state, priority, taskgroup_id)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, task.getName());
                    ps.setObject(2, task.getDescription());
                    ps.setObject(3, task.getDdl());
                    ps.setObject(4, task.getType());
                    ps.setObject(5, task.getState());
                    ps.setObject(6, task.getPriority());
                    ps.setObject(7, task.getTaskgroup_id());
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (DataAccessException e) {
            log.error("Dao: failed to insert the task: {}: {}", task.toString(), e.getMessage());
            return -1;
        }
    }
}
