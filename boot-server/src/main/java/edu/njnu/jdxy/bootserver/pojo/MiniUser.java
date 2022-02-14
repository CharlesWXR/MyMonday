package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiniUser implements Serializable {
    int id;
    String name;
    String staff_id;
    String email;
    String qq;
    String phone;
    String avatar_path;
    String title;
    int department_id;
}
