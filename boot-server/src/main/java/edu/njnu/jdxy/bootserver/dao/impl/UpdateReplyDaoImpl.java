package edu.njnu.jdxy.bootserver.dao.impl;

import edu.njnu.jdxy.bootserver.dao.UpdateReplyDao;
import edu.njnu.jdxy.bootserver.pojo.UpdateReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UpdateReplyDaoImpl implements UpdateReplyDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<UpdateReply> getRepliesByUpdateID(int updateID) {
        List<UpdateReply> replies = new ArrayList<UpdateReply>();
        try {
            String sql = "SELECT * FROM update_reply WHERE update_id = ?";
            replies = template.query(sql, new BeanPropertyRowMapper<UpdateReply>(UpdateReply.class), updateID);
        } catch (Exception e) {
            log.error("Dao: getReplies failed: updateID:{} :{}", updateID, e.getMessage());
        } finally {
            return replies;
        }
    }
}
