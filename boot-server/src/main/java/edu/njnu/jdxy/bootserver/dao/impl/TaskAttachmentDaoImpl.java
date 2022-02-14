package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.TaskAttachmentDao;
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
public class TaskAttachmentDaoImpl implements TaskAttachmentDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Attachment> getAttachmentsByUpdateID(int updateID) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        try {
            String sql = "SELECT * FROM task_attachment WHERE update_id = ?";
            attachments = template.query(sql, new BeanPropertyRowMapper<Attachment>(Attachment.class), updateID);
        } catch (DataAccessException e) {
            log.error("Dao: get attachments failed: updateID:{} :{}", updateID, e.getMessage());
        } finally {
            return attachments;
        }
    }

    @Override
    public List<Attachment> searchAttachmentByFileName(String filename) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        try {
            String sql = "SELECT * FROM task_attachment WHERE name = ?";
            attachments = template.query(sql, new BeanPropertyRowMapper<Attachment>(Attachment.class), filename);
        } catch (DataAccessException e) {
            log.error("Dao: search attachment with filename: {} : {}", filename, e.getMessage());
        } finally {
            return attachments;
        }
    }

    @Override
    public boolean addAttachment(Attachment attachment) {
        try {
            String sql = "INSERT INTO task_attachment(name, version, update_id, user_id, parent_id, path)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            return template.update(sql, attachment.getName(), attachment.getVersion(), attachment.getUpdate_id(),
                    attachment.getUser_id(), attachment.getParent_id(), attachment.getPath()) == 1;
        } catch (DataAccessException e) {
            log.error("Dao: add attachment failed:{} :{}", attachment.toString(), e.getMessage());
            return false;
        }
    }
}
