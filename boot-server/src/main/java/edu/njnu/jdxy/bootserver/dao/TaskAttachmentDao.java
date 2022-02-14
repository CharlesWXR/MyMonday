package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Attachment;

import java.util.List;

public interface TaskAttachmentDao {
    List<Attachment> getAttachmentsByUpdateID(int updateID);

    List<Attachment> searchAttachmentByFileName(String filename);

    boolean addAttachment(Attachment attachment);
}
