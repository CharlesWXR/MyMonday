package edu.njnu.jdxy.bootserver.service.impl;

import edu.njnu.jdxy.bootserver.dao.TaskDependencyDao;
import edu.njnu.jdxy.bootserver.dao.TaskOutputDao;
import edu.njnu.jdxy.bootserver.pojo.Attachment;
import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.pojo.TaskDependencyOutput;
import edu.njnu.jdxy.bootserver.service.DependencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class DependencyServiceImpl implements DependencyService {
    @Autowired
    private TaskDependencyDao taskDependencyDao;

    @Autowired
    private TaskOutputDao taskOutputDao;

    @Override
    public List<Task> getDependencies(int taskID) {
        return taskDependencyDao.getDependenciesByDestTaskID(taskID);
    }

    @Override
    public List<TaskDependencyOutput> getDependencyOutputs(int taskID) {
        List<TaskDependencyOutput> outputs = new ArrayList<TaskDependencyOutput>();
        List<Task> tasks = taskDependencyDao.getDependenciesByDestTaskID(taskID);

        for (Task task : tasks) {
            List<Attachment> o = taskOutputDao.getOutputsByTaskID(task.getId());
            TaskDependencyOutput t = new TaskDependencyOutput();
            t.setTask(task);
            t.setOutputs(o);
            outputs.add(t);
        }

        return outputs;
    }

    @Override
    public boolean setDependencies(List<Integer> fromIDs, int destTaskID) {
        return taskDependencyDao.deleteByDestTaskID(destTaskID)
                && taskDependencyDao.insertTaskDependencies(fromIDs, destTaskID);
    }
}
