package edu.njnu.jdxy.bootserver.service.impl;

import edu.njnu.jdxy.bootserver.dao.TaskOutputDao;
import edu.njnu.jdxy.bootserver.pojo.Attachment;
import edu.njnu.jdxy.bootserver.service.AttachmentService;
import edu.njnu.jdxy.bootserver.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    MinioUtil minioUtil;

    @Autowired
    TaskOutputDao taskOutputDao;

    @Override
    public InputStream getAttachmentByName(String name) {
        return minioUtil.downloadFile("/attachments/" + name);
    }

    @Override
    public List<Attachment> getOutputByTaskID(int taskID) {
        return taskOutputDao.getOutputsByTaskID(taskID);
    }
}
