--初始化数据库脚本
--创建数据库
Create database seckill;
--使用数据库
use seckill;
--创建秒杀库存表
create table seckill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
name varchar(120) NOT NULL COMMENT '商品名称',
number int NOT NULL COMMENT '库存数量',
create_time timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
start_time timestamp COMMENT '开始时间',
end_time timestamp  COMMENT '结束时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
 )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀库存表';

--初始化数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('100元秒杀iphone7',100,'2018-11-18 00:00:00','2018-11-20 00:00:00'),
  ('100元秒杀iphone6',100,'2018-11-18 00:00:00','2018-11-20 00:00:00'),
  ('100元秒杀iphone8',100,'2018-11-18 00:00:00','2018-11-20 00:00:00'),
  ('100元秒杀iphone9',100,'2018-11-18 00:00:00','2018-11-20 00:00:00');

--秒杀成功明细表
--用户登录相关明细信息
 create table success_killed(
seckill_id bitint not null COMMENT '秒杀商品id',
user_phone bitint not null COMMENT '用户手机号码',
state tinyint not null DEFAULT -1 COMMENT '状态标识：-1无效，0成功，1已付款，2已发货',
create_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seclikk_id,user_phone),/*联合主键，一个用户同一时间只可对一件商品秒杀*/
key idx_create_time(create_time)
 )ENGINE=InnoDB  DEFAULT CHARSET=UTF8 COMMENT='秒杀成功明细表';
--链接数据库的控制台
mysql -uroot -p --p:mysql