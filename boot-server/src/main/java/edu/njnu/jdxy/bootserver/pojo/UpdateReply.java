package edu.njnu.jdxy.bootserver.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UpdateReply {
    int id;
    String content;
    Date time;

    int update_id;
    int user_id;

    MiniUser user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTime() {
        return time;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setTime(Date time) {
        this.time = time;
    }
}
