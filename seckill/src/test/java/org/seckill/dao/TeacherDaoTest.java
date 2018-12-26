package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void addTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("黄仁达");
        teacher.setGender(0);
        teacher.setAge(50);
        Integer t=teacherDao.addTeacher(teacher);
        System.out.println("added:"+t);
    }

    @Test
    public void reduceTeacher() {
        Long teacherId=3L;
        Integer t=teacherDao.reduceTeacher(teacherId);
        System.out.println("deleted:"+t);
    }

    @Test
    public void updateTeacher() {
        Teacher teacher=new Teacher();
        teacher.setTeacherId(3L);
        teacher.setAge(80);
        Integer t=teacherDao.updateTeacher(teacher);
        System.out.println("updated:"+t);
    }

    @Test
    public void selectTeachers() {
        Teacher teacher = new Teacher();
        List<Teacher> teacherList=teacherDao.selectTeachers(teacher);
        for(Teacher t:teacherList){
            System.out.println(t);
        }
    }
}