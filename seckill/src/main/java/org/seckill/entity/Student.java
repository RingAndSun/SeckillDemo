package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private Long stuId;
    private String name;
    private Integer age;
    private Integer gender;
    private Date createTime;

    public Student() {}

    public Student(String name, Integer age, Integer gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
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
        return "Student{" +
                "stuId=" + stuId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", createTime=" + createTime +
                '}';
    }
}
