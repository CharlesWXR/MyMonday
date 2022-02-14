package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskOutputDao;
import edu.njnu.jdxy.bootserver.pojo.Attachment;
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
public class TaskOutputDaoImpl implements TaskOutputDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Attachment> getOutputsByTaskID(int taskID) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        try {
            String sql = "SELECT attachment_id FROM task_output WHERE task_id = ?";
            String queryForAttachment = "SELECT * FROM task_attachment WHERE id = ?";
            List<Integer> attachmentIDs = template.queryForList(sql, Integer.class, taskID);
            for (int i : attachmentIDs) {
                attachments.add(template.queryForObject(queryForAttachment, new BeanPropertyRowMapper<Attachment>(Attachment.class), i));
            }
        } catch (DataAccessException e) {
            log.error("Dao: get outputs failed: taskID: {} : {}", taskID, e.getMessage());
        } finally {
            return attachments;
        }
    }
}
