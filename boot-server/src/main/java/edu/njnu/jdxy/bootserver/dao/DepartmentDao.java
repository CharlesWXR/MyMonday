package edu.njnu.jdxy.bootserver.dao;

import edu.njnu.jdxy.bootserver.pojo.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> getDepartments();
}
