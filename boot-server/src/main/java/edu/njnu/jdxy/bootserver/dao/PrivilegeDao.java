package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Privilege;

import java.util.List;

public interface PrivilegeDao {
    List<Privilege> getPrivilegesByUserId(int id);
}
