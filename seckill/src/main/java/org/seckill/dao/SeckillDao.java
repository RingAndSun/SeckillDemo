package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDao {
    /*减库存
    * */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
    /*查找秒杀库存对象*/
    Seckill queryById(long seckillId);
    /*查找全部*/
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /*使用存储过程执行秒杀*/
    void killByProcedure(Map<String,Object> paramMap);

}
