package com.xudaweb.javaconcurrency.others;

/**
 * 雪花算法
 *
 * @author xuda
 */
public class SnowflakeIdGenerator {

    // 时间戳占用的位数
    private static final long TIMESTAMP_BITS = 41L;

    // 工作机器ID占用的位数
    private static final long WORKER_ID_BITS = 5L;

    // 数据中心ID占用的位数
    private static final long DATACENTER_ID_BITS = 5L;

    // 每毫秒生成的序列号占用的位数
    private static final long SEQUENCE_BITS = 12L;

    // 最大工作机器ID，可以分配的范围是0~31
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    // 最大数据中心ID，可以分配的范围是0~31
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // 时间戳的左移位数，等于序列号位数+工作机器ID位数+数据中心ID位数
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    // 数据中心ID的左移位数，等于序列号位数+工作机器ID位数
    private static final long DATACENTER_ID_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 工作机器ID的左移位数，等于序列号位数
    private static final long WORKER_ID_LEFT_SHIFT = SEQUENCE_BITS;

    // 序列号的最大值，等于2^SEQUENCE_BITS-1
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // 数据中心ID
    private final long datacenterId;

    // 工作机器ID
    private final long workerId;

    // 序列号
    private long sequence = 0L;

    // 上次生成ID的时间戳
    private long lastTimestamp = -1L;

    /**
     * 构造函数，传入数据中心ID和工作机器ID
     *
     * @param datacenterId 数据中心ID
     * @param workerId     工作机器ID
     */
    public SnowflakeIdGenerator(long datacenterId, long workerId) {
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + MAX_DATACENTER_ID + " or less than 0");
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    /**
     * 生成唯一ID
     *
     * @return 唯一ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上次生成ID的时间戳，说明系统时钟回退过，此时应抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }

        // 如果当前时间等于上次生成ID的时间戳（即同一毫秒内），则将序列号加1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0L) {
                // 如果序列号已经达到最大值，需要等待下一毫秒再继续生成ID
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        // 记录上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 组装唯一ID，先左移时间戳，再左移数据中心ID，然后左移工作机器ID，最后加上序列号
        return ((timestamp - 1627603200000L) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_LEFT_SHIFT)
                | (workerId << WORKER_ID_LEFT_SHIFT)
                | sequence;
    }

    /**
     * 获取下一毫秒的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 下一毫秒的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(2, 1);
        long id = idGenerator.nextId();
        System.out.println(id);
    }
}

