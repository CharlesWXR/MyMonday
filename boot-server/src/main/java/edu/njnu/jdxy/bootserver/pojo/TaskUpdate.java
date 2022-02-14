package edu.njnu.jdxy.bootserver.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class TaskUpdate {
    int id;
    String content;
    Date time;

    int task_id;
    int user_id;

    List<UpdateReply> replies;
    List<Attachment> attachments;
    MiniUser user;

    @Override
    public String toString() {
        return "TaskUpdate{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", task_id=" + task_id +
                ", user_id=" + user_id +
                ", replies=" + replies +
                ", attachments=" + attachments +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTime() {
        return time;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setTime(Date time) {
        this.time = time;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<UpdateReply> getReplies() {
        return replies;
    }

    public void setReplies(List<UpdateReply> replies) {
        this.replies = replies;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public MiniUser getUser() {
        return user;
    }

    public void setUser(MiniUser user) {
        this.user = user;
    }
}
