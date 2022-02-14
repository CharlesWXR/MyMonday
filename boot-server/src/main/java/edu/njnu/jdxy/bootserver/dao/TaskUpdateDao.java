package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.TaskUpdate;

import java.util.List;

public interface TaskUpdateDao {
    List<TaskUpdate> getTaskUpdatesByTaskID(int taskID);

    int insertTaskUpdate(int taskID, int userID, String content);
}
