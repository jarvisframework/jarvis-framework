package com.yx.framework.tool.util;


import org.apache.commons.lang3.StringUtils;


/**
 * Id工具类
 *
 * @author Administrator
 */
public class IdUtils {

    private static final String MACHINE_ID_STRING = "1";

    private static final String ID_FLAG = "1";

    /**
     * 起始的时间戳
     */
    private static final long START_STAMP = 1571097600000L;

    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12L;

    /**
     * 机器标识占用的位数
     */
    private static final long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private static final long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private static final long DATACENTER_ID = 1;

    /**
     * 机器标识
     */

    private static long machineId = 1;
    /**
     * 序列号
     */
    private static long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private static long lastStamp = -1L;


    /**
     * 产生下一个ID
     *
     * @return
     */
    public static synchronized String nextId() {

        if (StringUtils.isBlank(MACHINE_ID_STRING)) {
            throw new RuntimeException("配置文件读取错误(lnet.machineId)");
        }
        machineId = Long.valueOf(MACHINE_ID_STRING);
        long currStamp = System.currentTimeMillis();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;
        //时间戳部分
        long res = (currStamp - START_STAMP) << TIMESTAMP_LEFT
                //数据中心部分
                | DATACENTER_ID << DATACENTER_LEFT
                //机器标识部分
                | machineId << MACHINE_LEFT
                //序列号部分
                | sequence;
        //生成的ID+WEB（1）、APP（2）、API（3）的标识
        return res + ID_FLAG;
    }

    private static long getNextMill() {
        long mill = System.currentTimeMillis();
        while (mill <= lastStamp) {
            mill = System.currentTimeMillis();
        }
        return mill;
    }


}
