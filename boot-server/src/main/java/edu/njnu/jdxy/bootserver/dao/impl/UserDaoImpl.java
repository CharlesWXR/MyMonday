package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.UserDao;
import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public User validateUserByName(String userName, String password) {
        try {
            String sql = "SELECT * FROM user WHERE name = ? and password = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Dao: User: validateUserByName() return 0 result with params:" +
                    "name={} and password={}", userName, password);
            return null;
        }
    }

    @Override
    public User validateUserByEmail(String userEmail, String password) {
        try {
            String sql = "SELECT * FROM user WHERE email = ? and password = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userEmail, password);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Dao: User: validateUserByName() return 0 result with params:" +
                    "email={} and password={}", userEmail, password);
            return null;
        }
    }

    @Override
    public User validateUserByStaffId(String staff_id, String password) {
        try {
            String sql = "SELECT * FROM user WHERE staff_id = ? and password = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), staff_id, password);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Dao: User: validateUserByName() return 0 result with params:" +
                    "staff_id={} and password={}", staff_id, password);
            return null;
        }
    }

    @Override
    public MiniUser queryForMiniUserBuID(int id) {
        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<MiniUser>(MiniUser.class), id);
        } catch (Exception e) {
            log.error("Dao: User: queryMiniUser: failed with user_id={}: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public int insertUser(User user) {
        try {
            String sql = "INSERT INTO user(name, staff_id, password, email, qq, phone, title, department_id)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, user.getName());
                    ps.setObject(2, user.getStaff_id());
                    ps.setObject(3, user.getPassword());
                    ps.setObject(4, user.getEmail());
                    ps.setObject(5, user.getQq());
                    ps.setObject(6, user.getPhone());
                    ps.setObject(7, user.getTitle());
                    ps.setObject(8, user.getDepartment_id());
                    return ps;
                }
            }, keyHolder);
            log.info("Dao: User: successfully insert user of id: {}", keyHolder.getKey().intValue());
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            log.error("Dao: User: fail to insert user of name: {} -> {}", user.getName(), e.toString());
            return -1;
        }
    }

    @Override
    public List<MiniUser> searchUserByName(String userName) {
        List<MiniUser> res = new ArrayList<MiniUser>();
        try {
            String sql = "SELECT * FROM user WHERE name like ?";
            res = template.query(sql, new BeanPropertyRowMapper<MiniUser>(MiniUser.class), "%" + userName + "%");
        } catch (DataAccessException e) {
            log.error("Dao: User: fail to query for user with name:%{}%: {}", userName, e.getMessage());
        } finally {
            return res;
        }
    }

    @Override
    public List<MiniUser> searchUserByStaffId(String staff_id) {
        List<MiniUser> res = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user WHERE staff_id like ?";
            res = template.query(sql, new BeanPropertyRowMapper<MiniUser>(MiniUser.class), "%" + staff_id + "%");
        } catch (DataAccessException e) {
            log.error("Dao: User: fail to query for user with name:%{}%: {}", staff_id, e.getMessage());
        } finally {
            return res;
        }
    }
}
