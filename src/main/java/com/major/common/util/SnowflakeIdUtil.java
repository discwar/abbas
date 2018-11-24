package com.major.common.util;

/**
 * Snowflake算法工具类
 *
 * @author LianGuoQing
 */
public class SnowflakeIdUtil {

    private static SnowflakeIdWorker snowflakeIdWorker;

    public SnowflakeIdUtil(long workerId, long dataCenterId){
        snowflakeIdWorker = new SnowflakeIdWorker(workerId,dataCenterId);
    }

    public long nextId(){
        return snowflakeIdWorker.nextId();
    }

    public static void main(String[] args) {
        SnowflakeIdUtil idUtil = new SnowflakeIdUtil(1,1);
        System.out.println(idUtil.nextId());
    }

}
