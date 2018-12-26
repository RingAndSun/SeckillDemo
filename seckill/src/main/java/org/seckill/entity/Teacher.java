package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

public class Teacher implements Serializable {
    private Long teacherId;
    private String name;
    private Integer age;
    private Integer gender;
    private Date createTime;

    public Teacher() {}

    public Teacher(String name, Integer age, Integer gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", createTime=" + createTime +
                '}';
    }
}
