package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
                       "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList=seckillService.getSeckillList();
        logger.info("seckillList={}",seckillList);
    }

    @Test
    public void getById() {
        long id=1000L;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testSeckillLogic() {
        long id=1000L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone=17826879802L;
            try{
            SeckillExecution seckillExecution=seckillService.executeSeckill(id,phone,exposer.getMd5());
            logger.info("execution={}",seckillExecution);
            }catch (RepeatkillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e1){
                logger.error(e1.getMessage());
            }
        }else{
            logger.warn("exposer={}"+exposer);
        }
    }
    @Test
    public void executeSeckillProcedure(){
        long seckillId=1000L;
        long phone=17868956526L;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
        SeckillExecution execution=seckillService.executeSeckillProcedure(seckillId,phone,exposer.getMd5());
        logger.info(execution.getStateInfo());
        }
    }
}