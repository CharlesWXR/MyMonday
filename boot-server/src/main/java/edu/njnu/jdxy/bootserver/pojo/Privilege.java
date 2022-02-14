package edu.njnu.jdxy.bootserver.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Privilege implements Serializable {
    int privilege;
    int user_id;
    int department_id;
}
