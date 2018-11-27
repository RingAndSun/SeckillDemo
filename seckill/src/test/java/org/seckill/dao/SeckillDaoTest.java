package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
* 配置Spring和Junit的整合，Junit启动时加载SpringIOC容器
* spring-test,Junit
*/
@RunWith(SpringJUnit4ClassRunner.class)
//告诉JunitSpring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        System.out.println("secKillDao="+seckillDao);
        long id=1000;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckillList=seckillDao.queryAll(0,100);
        for(int i=0;i<seckillList.size();i++){
            System.out.println(seckillList.get(i));
        }
    }

    @Test
    public void reduceNumber() {
        Date killTime=new Date();
        int updateCount=seckillDao.reduceNumber(1000L,killTime);
        System.out.println(updateCount);
    }

}