package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
    private Long projectId;
    private String projectName;
    private Integer teacherId;
    private Date createTime;

    public Project() {}

    public Project(String projectName, Integer teacherId) {
        this.projectName = projectName;
        this.teacherId = teacherId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", teacherId=" + teacherId +
                ", createTime=" + createTime +
                '}';
    }
}
