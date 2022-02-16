package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.TaskDependencyOutput;
import edu.njnu.jdxy.bootserver.service.DependencyService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/dependency")
public class DependencyController {
    @Autowired
    private DependencyService dependencyService;

    @RequestMapping(value = "/{taskID}", method = RequestMethod.GET)
    public Result getDependencies(@PathVariable int taskID) {
        return Result.success(dependencyService.getDependencies(taskID));
    }

    @RequestMapping(value = "/output/{destTaskID}", method = RequestMethod.GET)
    public Result getDependencyOutputs(@PathVariable int destTaskID) {
        List<TaskDependencyOutput> outputs = dependencyService.getDependencyOutputs(destTaskID);
        return Result.success(outputs);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result setDependencies(@RequestBody Map<String, Object> params) {
        log.info("Controller: set dependencies invoked!");

        if (!params.containsKey("dependency_id") || !params.containsKey("task_id"))
            return Result.fail(ResultCode.BAD_REQUEST, null);

        List<Integer> tasks = (List<Integer>)params.get("dependency_id");
        int taskID = (int)params.get("task_id");
        if (dependencyService.setDependencies(tasks, taskID))
            return Result.success(null);
        else
            return Result.fail(ResultCode.BAD_REQUEST, null);
    }
}
