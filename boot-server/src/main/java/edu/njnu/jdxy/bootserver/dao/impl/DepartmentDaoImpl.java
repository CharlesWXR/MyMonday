package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.DepartmentDao;
import edu.njnu.jdxy.bootserver.pojo.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Department> getDepartments() {
        try {
            String sql = "SELECT * FROM department";
            return template.query(sql, new BeanPropertyRowMapper<Department>(Department.class));
        } catch (Exception e) {
            log.error("Dao: getDapartments: failed: {}", e.getMessage());
            return new ArrayList<Department>();
        }
    }
}
