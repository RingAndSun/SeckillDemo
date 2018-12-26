package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    public void addStudent() {
        Student student=new Student();
        student.setName("刘水宪");
        student.setGender(0);
        student.setAge(35);
        Integer successed = studentDao.addStudent(student);
        System.out.println("successed:"+successed);
    }

    @Test
    public void reduceStudent() {
        Long stuId=1004L;
        Integer s = studentDao.reduceStudent(stuId);
        System.out.println("deleted:"+s);
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setStuId(1004L);
        student.setAge(1000);
        student.setGender(1);
        Integer s = studentDao.updateStudent(student);
        System.out.println("update:"+s);
    }

    @Test
    public void selectStudents() {
        Student student =new Student();
        List<Student> studentList=studentDao.selectStudents(student);
        for(Student s:studentList){
            System.out.println(s);
        }
    }
}