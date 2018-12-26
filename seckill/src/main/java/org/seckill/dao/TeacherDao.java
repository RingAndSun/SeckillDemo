package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Teacher;

import java.util.List;

public interface TeacherDao {
    int addTeacher(Teacher teacher);
    int reduceTeacher(@Param("teacherId") Long teacherId);
    int updateTeacher(Teacher teacher);
    List<Teacher> selectTeachers(Teacher teacher);
}
