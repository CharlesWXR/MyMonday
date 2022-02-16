package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.WorkspaceDao;
import edu.njnu.jdxy.bootserver.pojo.Workspace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class WorkspaceDaoImpl implements WorkspaceDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Workspace> getWorkspacesByDepartmentID(int id) {
        try {
            String sql = "SELECT * FROM workspace WHERE department_id = ?";
            return template.query(sql, new BeanPropertyRowMapper<Workspace>(Workspace.class), id);
        } catch (Exception e) {
            log.error("Dao: Workspace: fail to fetch workspaces where departmentID={}: {}", id, e.getMessage());
            return new ArrayList<Workspace>();
        }
    }

    @Override
    public int getWorkspaceIDByTaskgroupID(int taskgroupID) {
        try {
            String sql = "SELECT workspace_id FROM taskgroup WHERE id = ?";
            return template.queryForObject(sql, Integer.class, taskgroupID);
        } catch (DataAccessException e) {
            log.error("Dao: Taskgroup: fail to fetch workspaceIDs where taskgroupID={}: {}", taskgroupID, e.getMessage());
            return -1;
        }
    }
}
