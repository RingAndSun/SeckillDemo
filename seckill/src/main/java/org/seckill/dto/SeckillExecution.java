package org.seckill.dto;

import org.seckill.entity.Successkilled;
import org.seckill.enums.SeckillStateNum;

import java.io.Serializable;

/**
 * 秒杀执行后的结果信息
 */
public class SeckillExecution implements Serializable {
    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //状态信息
    private String stateInfo;
    //返回秒杀成功的对象
    private Successkilled successkilled;

    public SeckillExecution(long seckillId, SeckillStateNum stateNum, Successkilled successkilled) {
        this.seckillId = seckillId;
        this.state = stateNum.getState();
        this.stateInfo = stateNum.getStateInfo();
        this.successkilled = successkilled;
    }

    public SeckillExecution(long seckillId,  SeckillStateNum stateNum) {
        this.seckillId = seckillId;
        this.state = stateNum.getState();
        this.stateInfo = stateNum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Successkilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(Successkilled successkilled) {
        this.successkilled = successkilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successkilled=" + successkilled +
                '}';
    }
}
