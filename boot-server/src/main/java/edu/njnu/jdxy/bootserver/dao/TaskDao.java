package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.MiniUser;
import edu.njnu.jdxy.bootserver.pojo.Task;

import java.util.List;

public interface TaskDao {
    List<Task> getTasksByTaskGroupID(int id);

    List<MiniUser> getInitiatorsByTaskID(int id);

    List<MiniUser> getAccepterByTaskID(int id);

    boolean updateTask(int targetID, String attr, Object newVal);

    boolean deleteTaskInitiator(int taskID);

    boolean updateTaskInitiator(int targetID, List<Integer> initiatorsIDs);

    boolean deleteTaskAccepter(int taskID);

    boolean updateTaskAccepter(int taskID, List<Integer> accepterIDs);
}
