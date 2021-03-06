package org.seckill.enums;

public enum SeckillStateNum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"秒杀重复"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");
    private int state;
    private String stateInfo;

    SeckillStateNum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    public static SeckillStateNum stateOf(int index){
        for (SeckillStateNum stateNum:values()){
            if(stateNum.getState()==index){
                return stateNum;
            }
        }
        return null;
    }
}
