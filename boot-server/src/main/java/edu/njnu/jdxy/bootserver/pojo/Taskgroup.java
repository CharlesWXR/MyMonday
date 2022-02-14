package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Taskgroup {
    int id;
    String name;
    String description;

    int workspace_id;

    List<Task> tasks;
}
