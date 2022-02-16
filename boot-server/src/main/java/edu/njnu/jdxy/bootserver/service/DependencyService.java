package edu.njnu.jdxy.bootserver.service;

import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.pojo.TaskDependencyOutput;

import java.util.List;

public interface DependencyService {
    List<Task> getDependencies(int taskID);

    List<TaskDependencyOutput> getDependencyOutputs(int taskID);

    boolean setDependencies(List<Integer> fromIDs, int destTaskID);
}
