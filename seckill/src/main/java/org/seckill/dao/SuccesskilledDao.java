package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Successkilled;

public interface SuccesskilledDao {
    int insertSuccesskilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
    Successkilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
