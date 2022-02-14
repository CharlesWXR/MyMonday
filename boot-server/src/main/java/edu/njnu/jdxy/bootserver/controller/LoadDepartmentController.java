package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Department;
import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.service.TaskService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/department")
public class LoadDepartmentController {
    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result getDepartments() {
        log.info("Controller: getWorkspaces invoked");
        List<Department> departments = taskService.getDepartments();
        if (departments.size() > 0) {
            return Result.success(departments);
        }
        else {
            return Result.fail(ResultCode.NO_TASK_EXIST, departments);
        }
    }
}
