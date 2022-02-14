package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Taskgroup;
import edu.njnu.jdxy.bootserver.pojo.Workspace;

import java.util.List;

public interface WorkspaceDao {
    List<Workspace> getWorkspacesByDepartmentID(int id);
}
