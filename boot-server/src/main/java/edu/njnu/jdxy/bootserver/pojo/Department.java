package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Department {
    int id;
    String name;
    String description;

    List<Workspace> workspaces;
}
