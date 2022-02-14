package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
    int id;
    String name;
    String staff_id;
    String password;
    String email;
    String qq;
    String phone;
    String avatar_path;
    String title;
    int department_id;

    List<Privilege> privileges;
}
