package edu.njnu.jdxy.bootserver.service.impl;

import edu.njnu.jdxy.bootserver.dao.DepartmentDao;
import edu.njnu.jdxy.bootserver.dao.TaskDao;
import edu.njnu.jdxy.bootserver.dao.TaskgroupDao;
import edu.njnu.jdxy.bootserver.dao.WorkspaceDao;
import edu.njnu.jdxy.bootserver.pojo.Department;
import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import edu.njnu.jdxy.bootserver.pojo.Workspace;
import edu.njnu.jdxy.bootserver.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private TaskgroupDao taskgroupDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<Department> getDepartments() {
        List<Department> res = departmentDao.getDepartments();
        if (res.size() > 0) {
            res.remove(0);
            for (Department department : res)
                department.setWorkspaces(workspaceDao.getWorkspacesByDepartmentID(department.getId()));
        }
        return res;
    }

    @Override
    public List<Workspace> getWorkspacesByDepartmentID(int id) {
        return workspaceDao.getWorkspacesByDepartmentID(id);
    }

    @Override
    public List<Taskgroup> getTaskgroupsByWorkspaceID(int id) {
        List<Taskgroup> taskgourps = taskgroupDao.getTaskgroupsByWorkspaceID(id);
        if (null != taskgourps) {
            for (Taskgroup taskgroup : taskgourps) {
                List<Task> tasks = taskDao.getTasksByTaskGroupID(taskgroup.getId());
                if (tasks != null) {
                    for (Task task : tasks) {
                        task.setInitiators(taskDao.getInitiatorsByTaskID(task.getId()));
                        task.setAccepters(taskDao.getAccepterByTaskID(task.getId()));
                    }
                }
                taskgroup.setTasks(tasks);
            }
        }
        return taskgourps;
    }

    @Override
    public List<Task> getTasksByTaskGroupID(int id) {
        return taskDao.getTasksByTaskGroupID(id);
    }

    @Override
    public boolean updateTask(int taskID, String attr, Object newVal) {
        return taskDao.updateTask(taskID, attr, newVal);
    }

    @Override
    public boolean updateTaskInitiator(int taskID, List<Integer> initiators) {
        taskDao.deleteTaskInitiator(taskID);
        return taskDao.updateTaskInitiator(taskID, initiators);
    }

    @Override
    public boolean updateTaskAcceptor(int taskID, List<Integer> acceptors) {
        taskDao.deleteTaskAccepter(taskID);
        return taskDao.updateTaskAccepter(taskID, acceptors);
    }
}
