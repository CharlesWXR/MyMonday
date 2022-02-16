package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.util.List;

@Data
public class TaskDependencyOutput {
    private Task task;
    private List<Attachment> outputs;
}
