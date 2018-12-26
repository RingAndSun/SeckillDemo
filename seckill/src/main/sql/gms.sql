--初始化数据库脚本
--创建数据库
create database gms;
--使用数据库
use gms;
--创建学生表
create table student(
stu_id bigint NOT null AUTO_INCREMENT COMMENT 'stu ID',
name varchar (120) not null COMMENT 'stu name',
gender int(1) not null comment 'gender:0 male 1 female',
age int(4) not null comment 'age',
create_time timestamp default CURRENT_TIMESTAMP COMMENT 'sign up time',
PRIMARY KEY (stu_id),
key idx_stu_id(stu_id),
key idx_name(name),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='学生信息表';
--初始化数据
insert into
    student(name,gender,age)
values
    ('张玉成',1,20),
    ('李成强',0,21),
    ('赵祥文',0,22),
    ('林雪',1,20);
--创建教师表
create table teacher(
teacher_id bigint NOT null AUTO_INCREMENT COMMENT 'teacher ID',
name varchar (120) not null COMMENT 'teacher name',
gender int(1) not null comment 'gender:0 male 1 female',
age int(4) not null comment 'age',
create_time timestamp default CRUUENT_TIMESTAMP COMMENT 'sign up time',
PRIMARY KEY (teacher_id),
key idx_teacher_id(teacher_id),
key idx_name(name),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=UTF8 COMMENT='教师信息表';
--初始化数据
insert into
    teacher(name,gender,age)
values
    ('李玲',1,26),
    ('陈珂',0,28);
--创建成绩表
create table project(
project_id bigint not null AUTO_INCREMENT COMMENT 'project ID',
project_name varchar(45) not null COMMENT 'project name',
teacher_id bigint COMMENT 'teacher',
create_time timestamp  default  CRUUENT_TIMESTAMP COMMENT 'create time',
PRIMARY KEY (project_id),
key idx_project_id(project_id),
key idx_project_name(project_name),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT  CHARSET=UTF8 COMMENT='课程信息表';
--初始化数据
insert into
    project(project_name,teacher_id)
values
    ('基础术控',1),
    ('黑魔法',0);
--创建成绩表
create table scoreSheet(
score_id bigint not null AUTO_INCREMENT COMMENT 'score id',
stu_id bigint not null COMMENT 'secor student',
project_id bigint not null COMMENT 'project',
teacher_id bigint not null COMMENT 'teacher',
score int(10) not null COMMENT 'achievement',
create_time timestamp default CRUUENT_TIMESTAMP COMMENT 'create time',
primary key (score_id),
key idx_score_id(score_id),
key idx_stu_id(stu_id),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=UTF8 COMMENT='成绩表';
--初始化数据
insert into
    scoreSheet(stu_id,project_id,teacher_id,score)
values
    (1000,0,1,325),
    (1000,1,0,412),
    (1001,0,1,388),
    (1001,1,0,400),
    (1002,1,0,390),
    (1002,0,1,434);
