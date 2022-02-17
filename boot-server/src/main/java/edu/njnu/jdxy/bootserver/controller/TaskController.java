package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.service.TaskService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getAllTasks() {
        return Result.success(taskService.getAllTasks());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result createTask(@RequestBody Map<String, Object> params) {
        log.info("Controller: create task invoked!");

        if (!params.containsKey("task_initiators"))
            return Result.fail(ResultCode.BAD_REQUEST, null);

        Task task = new Task();
        List<Integer> initiators = new ArrayList<Integer>();
        try {
            BeanUtils.populate(task, params);
            initiators = (List<Integer>) params.get("task_initiators");
        } catch (Exception e) {
            log.error("Controller: create task failed: params: {}: {}", params, e.getMessage());
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }

        log.info(task.toString());

        if (taskService.createTask(task, initiators))
            return Result.success(null);
        else
            return Result.fail(ResultCode.BAD_REQUEST, null);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result updateTask(@RequestBody Map<String, Object> params) {
        log.info("Controller: updateTask invoked");

        if (!params.containsKey("task_id") || !params.containsKey("attr") || !params.containsKey("new_val"))
            return Result.fail(ResultCode.BAD_REQUEST, null);

        int taskID = (int)params.get("task_id");
        String attr = (String)params.get("attr");
        Object newVal = params.get("new_val");
        if (taskService.updateTask(taskID, attr, newVal)) {
            return Result.success(null);
        }
        else {
            return Result.fail(ResultCode.NO_TASK_EXIST, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/initiator", method = RequestMethod.PUT)
    public Result updateTaskInitiator(@RequestBody Map<String, Object> params) {
        log.info("Controller: update initiator invoked");
        if (!params.containsKey("initiators") && !params.containsKey("task_id"))
            return Result.fail(ResultCode.BAD_REQUEST, null);

        List<Integer> userIDs = (List<Integer>) params.get("initiators");
        int taskID = (int) params.get("task_id");
        if (userIDs != null && taskService.updateTaskInitiator(taskID, userIDs)) {
            return Result.success(null);
        }
        else {
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/accepter", method = RequestMethod.PUT)
    public Result updateTaskAccepter(@RequestBody Map<String, Object> params) {
        log.info("Controller: update accepter invoked");
        if (!params.containsKey("accepters") && !params.containsKey("task_id"))
            return Result.fail(ResultCode.BAD_REQUEST, null);

        List<Integer> userIDs = (List<Integer>) params.get("accepters");
        int taskID = (int) params.get("task_id");
        if (userIDs != null && taskService.updateTaskAcceptor(taskID, userIDs))
            return Result.success(null);
        else
            return Result.fail(ResultCode.BAD_REQUEST, null);
    }
}
