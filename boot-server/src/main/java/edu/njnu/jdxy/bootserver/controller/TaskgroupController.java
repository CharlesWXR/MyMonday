package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import edu.njnu.jdxy.bootserver.service.TaskService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/taskgroup")
public class TaskgroupController {
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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result createTaskgroup(@RequestParam("taskgroup_name")String taskgroupName,
                                  @RequestParam("workspace_id") int workspaceID) {
        log.info("Controller: create taskgroup invoked!");
        if (taskService.createTaskgroup(taskgroupName, workspaceID))
            return Result.success(null);
        else
            return Result.fail(ResultCode.BAD_REQUEST, null);
    }

    @RequestMapping(value = "/workspace", method = RequestMethod.GET)
    public Result getWorkspaceIDByTaskgroup(@RequestParam("taskgroup_id")int id) {
        log.info("Controller: get taskgroup's workspace id");
        return Result.success(taskService.getWorkspaceIDByTaskgroupID(id));
    }
}
