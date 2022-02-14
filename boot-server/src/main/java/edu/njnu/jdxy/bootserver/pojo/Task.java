package edu.njnu.jdxy.bootserver.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Task {
    int id;
    String name;
    String description;
    Date init_time;
    Date ddl;
    int type;
    int state;
    int priority;

    int taskgroup_id;

    List<MiniUser> initiators;
    List<MiniUser> accepters;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", init_time=" + init_time +
                ", ddl=" + ddl +
                ", type=" + type +
                ", state=" + state +
                ", priority=" + priority +
                ", taskgroup_id=" + taskgroup_id +
                ", initiators=" + initiators +
                ", accepters=" + accepters +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getInit_time() {
        return init_time;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setInit_time(Date init_time) {
        this.init_time = init_time;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getDdl() {
        return ddl;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTaskgroup_id() {
        return taskgroup_id;
    }

    public void setTaskgroup_id(int taskgroup_id) {
        this.taskgroup_id = taskgroup_id;
    }

    public List<MiniUser> getInitiators() {
        return initiators;
    }

    public void setInitiators(List<MiniUser> initiators) {
        this.initiators = initiators;
    }

    public List<MiniUser> getAccepters() {
        return accepters;
    }

    public void setAccepters(List<MiniUser> accepters) {
        this.accepters = accepters;
    }
}
