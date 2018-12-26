package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Project;

import java.util.List;

public interface ProjectDao {
    int addProject(Project project);
    int reduceProject(@Param("projectId") Long projectId);
    int updateProject(Project project);
    List<Project> selectProjects(Project project);
}
