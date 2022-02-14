package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.Task;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import edu.njnu.jdxy.bootserver.service.TaskService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result getTaskgroupsByWorkspaceID(@RequestParam("workspace_id")int id) {
        log.info("Controller: getTaskgroups invoked");
        List<Taskgroup> res = taskService.getTaskgroupsByWorkspaceID(id);
        if (res.size() == 0) {
            return Result.fail(ResultCode.NO_TASK_EXIST, res);
        }
        else {
            return Result.success(res);
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result updateTask(@RequestBody Map<String, Object> params) {
        log.info("Controller: updateTask invoked");

        if (params.containsKey("task_id") == false || params.containsKey("attr") == false || params.containsKey("new_val") == false)
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
