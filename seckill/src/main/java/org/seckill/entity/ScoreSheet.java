package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

public class ScoreSheet implements Serializable {
    private Long scoreId;
    private Long stuId;
    private Long teacherId;
    private Long projectId;
    private Integer score;
    private Date createTime;

    public ScoreSheet() {
    }

    public ScoreSheet(Long stuId, Long teacherId, Long projectId, Integer score) {
        this.stuId = stuId;
        this.teacherId = teacherId;
        this.projectId = projectId;
        this.score = score;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ScoreSheet{" +
                "scoreId=" + scoreId +
                ", stuId=" + stuId +
                ", teacherId=" + teacherId +
                ", projectId=" + projectId +
                ", score=" + score +
                ", createTime=" + createTime +
                '}';
    }
}
