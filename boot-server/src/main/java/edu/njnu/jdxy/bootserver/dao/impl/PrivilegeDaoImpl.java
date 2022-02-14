package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.PrivilegeDao;
import edu.njnu.jdxy.bootserver.pojo.Privilege;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PrivilegeDaoImpl implements PrivilegeDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Privilege> getPrivilegesByUserId(int id) {
        try {
            String sql = "SELECT * FROM privilege WHERE user_id = ?";
            return template.query(sql, new BeanPropertyRowMapper<>(Privilege.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
