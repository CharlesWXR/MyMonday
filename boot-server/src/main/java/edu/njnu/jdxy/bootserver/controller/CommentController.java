package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.pojo.TaskUpdate;
import edu.njnu.jdxy.bootserver.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private UpdateService updateService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getUpdates(@RequestParam("task_id") int taskID) {
        log.info("Contorller: get updates invoked!");

        List<TaskUpdate> updates = updateService.getUpdatesByTaskID(taskID);
        return Result.success(updates);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result postUpdates(HttpServletRequest request) {
        log.info("Controller: post updates invoked!");
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("attachments");
        int userID = Integer.parseInt(request.getParameter("user_id"));
        int taskID = Integer.parseInt(request.getParameter("task_id"));
        String content = request.getParameter("content");
        log.info(files.toString());
        List<String> urls = updateService.postUpdates(files, taskID, userID, content);

        return Result.success(urls);
    }
}
