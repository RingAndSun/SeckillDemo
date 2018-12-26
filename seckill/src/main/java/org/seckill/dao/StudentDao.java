package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Student;

import java.util.List;

/**
 * 实现学生的增删改查
 */
public interface StudentDao {
    int addStudent(Student student);
    int reduceStudent(@Param("stuId") Long stuId);
    int updateStudent(Student student);
    List<Student> selectStudents(Student student);
}
