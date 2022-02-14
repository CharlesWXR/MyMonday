package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Attachment;

import java.util.List;

public interface TaskOutputDao {
    List<Attachment> getOutputsByTaskID(int taskID);

    boolean deleteOutputByTaskID(int taskID);

    boolean insertOutput(List<Integer> attachmentIDs, int taskID);
}
