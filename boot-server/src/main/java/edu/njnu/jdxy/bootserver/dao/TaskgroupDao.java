package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Taskgroup;

import java.util.List;

public interface TaskgroupDao {
    List<Taskgroup> getTaskgroupsByWorkspaceID(int workspaceID);
}
