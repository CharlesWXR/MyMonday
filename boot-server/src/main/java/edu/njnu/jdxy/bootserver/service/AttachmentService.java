package edu.njnu.jdxy.bootserver.service;

import edu.njnu.jdxy.bootserver.pojo.Attachment;

import java.io.InputStream;
import java.util.List;

public interface AttachmentService {
    InputStream getAttachmentByName(String name);

    List<Attachment> getOutputByTaskID(int taskID);

    List<Attachment> getAttachmentsByTaskID(int taskID);

    boolean addOutputs(List<Integer> attachmentIDs, int taskID);
}
