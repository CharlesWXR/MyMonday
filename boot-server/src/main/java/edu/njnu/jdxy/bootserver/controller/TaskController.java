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
public class LoadTaskController {
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

        if (params.containsKey("task") == false || params.containsKey("attr") == false)
            return Result.fail(ResultCode.BAD_REQUEST, null);

        Task newVal = new Task();
        Map<String, Object> taskMap = (Map<String, Object>)params.get("task");
        try {
            ConvertUtils.register(new Converter() {
                public Object convert(Class type, Object value) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        return simpleDateFormat.parse(value.toString());
                    } catch (Exception e) {
                        log.error("BeanUtil Converter: Fail to convert string to date: {}", value);
                    }
                    return null;
                }
            }, Date.class);

            BeanUtils.populate(newVal, taskMap);
        } catch (Exception e) {
            log.error("Controller: updateTask: {}: params={}", e.getMessage(), params);
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }

        String attr = (String)params.get("attr");
        if (taskService.updateTask(attr, newVal)) {
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
        if (taskService.updateTaskInitiator(taskID, userIDs)) {
            return Result.success(null);
        }
        else {
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }
    }
}
