package edu.njnu.jdxy.bootserver.service;

import edu.njnu.jdxy.bootserver.pojo.TaskUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdateService {
    List<TaskUpdate> getUpdatesByTaskID(int taskID);

    List<String> postUpdates(List<MultipartFile> files, int taskID, int userID, String content);
}
