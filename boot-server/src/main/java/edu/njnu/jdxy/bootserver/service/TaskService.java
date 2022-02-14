package edu.njnu.jdxy.bootserver.service;

import edu.njnu.jdxy.bootserver.pojo.Department;
import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import edu.njnu.jdxy.bootserver.pojo.Workspace;

import java.util.List;

public interface TaskService {
    List<Department> getDepartments();

    List<Workspace> getWorkspacesByDepartmentID(int id);

    List<Taskgroup> getTaskgroupsByWorkspaceID(int id);

    List<Task> getTasksByTaskGroupID(int id);

    boolean updateTask(int taskID, String attr, Object newVal);

    boolean updateTaskInitiator(int taskID, List<Integer> initiators);

    boolean updateTaskAcceptor(int taskID, List<Integer> acceptors);
}
