package edu.njnu.jdxy.bootserver.controller;

import edu.njnu.jdxy.bootserver.pojo.Attachment;
import edu.njnu.jdxy.bootserver.pojo.Result;
import edu.njnu.jdxy.bootserver.service.AttachmentService;
import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void getAttachmentUrl(@RequestParam("attachment_name")String attachmentName, HttpServletResponse response) {
        InputStream is = attachmentService.getAttachmentByName(attachmentName);

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;fileName=" + attachmentName);

        byte[] buffer = new byte[1024 * 1024];
        try {
            OutputStream out = response.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            int i = bis.read(buffer);
            while (i != -1) {
                out.write(buffer, 0, i);
                out.flush();
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            log.error("Controller: get attachment failed: {}: {}", attachmentName, e.getMessage());
        }
    }

    @RequestMapping(value = "/{taskID}", method = RequestMethod.GET)
    public Result getAttachments(@PathVariable int taskID) {
        List<Attachment> attachments = attachmentService.getAttachmentsByTaskID(taskID);
        return Result.success(attachments);
    }

    @RequestMapping(value = "/output", method = RequestMethod.GET)
    public Result getOutputs(@RequestParam("task_id")int taskID) {
        List<Attachment> attachments = attachmentService.getOutputByTaskID(taskID);
        return Result.success(attachments);
    }

    @RequestMapping(value = "/output", method = RequestMethod.PUT)
    public Result addOutputs(@RequestBody Map<String, Object> params) {
        log.info("Service: addOutputs invoked!");
        if (!params.containsKey("task_id") || !params.containsKey("attachment_id")) {
            return Result.fail(ResultCode.BAD_REQUEST, null);
        }

        List<Integer> attachmentIDs = (List<Integer>) params.get("attachment_id");
        int taskID = (int) params.get("task_id");
        if (attachmentService.addOutputs(attachmentIDs, taskID))
            return Result.success(null);
        else
            return Result.fail(ResultCode.BAD_REQUEST, null);
    }
}
