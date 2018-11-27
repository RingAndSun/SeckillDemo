package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccesskilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.seckill.enums.SeckillStateNum;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccesskilledDao successkilledDao;
    @Autowired
    private RedisDao redisDao;
    //md5盐值 用于混淆md5
    private final String slat = "suadguaduagyudw&*@&#^&yg&*Y&hh&*GdeGGYDSYG8";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 进行一系列判断，产生是否把秒杀接口返回给用户
     * 暴露秒杀接口
     *
     * @param seckillId
     * @return Exposer
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //優化點：緩存優化
        //訪問redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
            //訪問數據庫
        seckill = seckillDao.queryById(seckillId);
            if (seckill==null){
                return new Exposer(false,seckillId);
            }
            else{
                //放入redis
                redisDao.putSeckill(seckill);
            }
        }
        //如果查不到该产品则返回产品秒杀关闭的Dto
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        //产品存在判断秒杀时间是否合法
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        //如果时间不和发则返回参数完全但是秒杀关闭的Dto
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //完全符合条件则返回成功的dto
        //对字符串的一个不可逆的加密
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 执行秒杀逻辑
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return SeckillException
     * @throws SeckillCloseException
     * @throws RepeatkillException
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillCloseException, RepeatkillException {
        //判断是否被篡改
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //記錄購買行為,如果復合主鍵衝突影響記錄數就為0
            int insertCount = successkilledDao.insertSuccesskilled(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatkillException("seckill repeated");
            } else {
                //減庫存
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //沒有更新到記錄，秒殺結束
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功
                    Successkilled successkilled = successkilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateNum.SUCCESS, successkilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatkillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("success inner error:" + e.getMessage());
        }
    }

    /**
     * 调用存储过程来只执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillCloseException
     * @throws RepeatkillException
     */
    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if(md5==null|| !md5.equals(getMd5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStateNum.DATA_REWRITE);
        }
        Date killTime=new Date();
        Map<String,Object> map=new HashMap<>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程，result被赋值
        try {
            seckillDao.killByProcedure(map);
            int result=MapUtils.getInteger(map,"result",-2);
            if(result==1){
                Successkilled sk=successkilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStateNum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillId,SeckillStateNum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStateNum.INNER_ERROR);
        }
    }

    /**
     * 对秒杀产品编码加密
     * @param seckillId
     * @return String
     */
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
