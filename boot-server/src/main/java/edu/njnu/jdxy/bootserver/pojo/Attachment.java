package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

@Data
public class TaskAttachment {
    int id;
    String name;
    String version;

    int task_id;
    int user_id;
}
