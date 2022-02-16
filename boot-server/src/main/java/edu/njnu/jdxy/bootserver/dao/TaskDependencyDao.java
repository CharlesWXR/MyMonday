package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Task;

import java.util.List;

public interface TaskDependencyDao {
    List<Task> getDependenciesByDestTaskID(int taskID);

    List<Task> getDependenciesByFromTaskID(int taskID);

    boolean deleteByDestTaskID(int taskID);

    boolean insertTaskDependencies(List<Integer> fromTaskIDs, int destTaskID);
}
