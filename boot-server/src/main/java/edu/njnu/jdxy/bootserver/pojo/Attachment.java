package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

@Data
public class Attachment {
    int id;
    String name;
    String version;
    String path;

    int update_id;
    int user_id;
    int parent_id;
}
