package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskgroupDao;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
