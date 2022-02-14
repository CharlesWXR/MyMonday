package edu.njnu.jdxy.bootserver.service.impl;

import edu.njnu.jdxy.bootserver.dao.TaskAttachmentDao;
import edu.njnu.jdxy.bootserver.dao.TaskUpdateDao;
import edu.njnu.jdxy.bootserver.dao.UpdateReplyDao;
import edu.njnu.jdxy.bootserver.dao.UserDao;
import edu.njnu.jdxy.bootserver.pojo.Attachment;
import edu.njnu.jdxy.bootserver.pojo.TaskUpdate;
import edu.njnu.jdxy.bootserver.pojo.UpdateReply;
import edu.njnu.jdxy.bootserver.service.UpdateService;
import edu.njnu.jdxy.bootserver.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private UpdateReplyDao updateReplyDao;

    @Autowired
    private TaskAttachmentDao taskAttachmentDao;

    @Autowired
    private TaskUpdateDao taskUpdateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MinioUtil minioUtil;

    private String FILEPATH = "attachments/";

    @Override
    public List<TaskUpdate> getUpdatesByTaskID(int taskID) {
        List<TaskUpdate> updates = taskUpdateDao.getTaskUpdatesByTaskID(taskID);
        for (TaskUpdate update : updates) {
            update.setAttachments(taskAttachmentDao.getAttachmentsByUpdateID(update.getId()));
            update.setUser(userDao.queryForMiniUserBuID(update.getUser_id()));
            update.setReplies(updateReplyDao.getRepliesByUpdateID(update.getId()));
            for (UpdateReply reply : update.getReplies()) {
                reply.setUser(userDao.queryForMiniUserBuID(reply.getId()));
            }
        }
        return updates;
    }

    @Override
    public List<String> postUpdates(List<MultipartFile> files, int taskID, int userID, String content) {
        int updateID = taskUpdateDao.insertTaskUpdate(taskID, userID, content);
        if (updateID == -1)
            return null;

        List<String> urls = new ArrayList<String>();
        for (MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setUpdate_id(updateID);
            attachment.setUser_id(userID);

            String filename = file.getOriginalFilename();
            int pos = Math.max(filename.lastIndexOf('\\'), filename.lastIndexOf('/'));
            if (pos != -1)
                filename = filename.substring(pos + 1);

            List<Attachment> res = taskAttachmentDao.searchAttachmentByFileName(filename);
            int version = res.size();
            if (version > 0) {
                int p = filename.lastIndexOf('.');
                filename =  filename.substring(0, p) + "-" + version + filename.substring(p);
                attachment.setParent_id(res.get(0).getId());
            }
            attachment.setName(filename);
            attachment.setVersion(Integer.toString(version));

            filename = FILEPATH + filename;
            attachment.setPath(filename);

            try {
                urls.add(minioUtil.uploadFile(file.getInputStream(), filename, file.getContentType()));
                taskAttachmentDao.addAttachment(attachment);
            } catch (IOException e) {
                urls.add(null);
                log.error("Service: upload file to minio: {}", e.getMessage());
            }
        }
        return urls;
    }
}
