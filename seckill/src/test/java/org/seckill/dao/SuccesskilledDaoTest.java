package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Successkilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccesskilledDaoTest {
    @Resource
    private SuccesskilledDao successkilledDao;

    @Test
    public void insertSuccesskilled() {
        System.out.println("SuccesskilledDao:"+successkilledDao);
        long seckillId=1001L;
        long userPhone=17826879803L;
        int insertCount=successkilledDao.insertSuccesskilled(seckillId,userPhone);
        System.out.println("sssnssusu==="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId=1001L;
        long userPhone=17826879803L;
        Successkilled successkilled=successkilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println("successkilled:"+successkilled);
        System.out.println("successkilled.Seckill:"+successkilled.getSeckill());
    }
}