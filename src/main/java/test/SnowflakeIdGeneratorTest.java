package test;

import com.xudaweb.javaconcurrency.others.SnowflakeIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SnowflakeIdGeneratorTest {

    @Test
    public void testNextId() {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);
        long lastId = 0L;
        for (int i = 0; i < 1000000; i++) {
            long id = idGenerator.nextId();
            Assertions.assertTrue(id > lastId);
            lastId = id;
        }
    }

    @Test
    public void testNextId1() throws InterruptedException {
        SnowflakeIdGenerator idGenerator1 = new SnowflakeIdGenerator(1, 1);
        SnowflakeIdGenerator idGenerator2 = new SnowflakeIdGenerator(2, 1);
        SnowflakeIdGenerator idGenerator3 = new SnowflakeIdGenerator(1, 2);
        SnowflakeIdGenerator idGenerator4 = new SnowflakeIdGenerator(2, 2);

        long lastId1 = 0L;
        long lastId2 = 0L;
        long lastId3 = 0L;
        long lastId4 = 0L;

        for (int i = 0; i < 1000000; i++) {
            long id1 = idGenerator1.nextId();
            long id2 = idGenerator2.nextId();
            long id3 = idGenerator3.nextId();
            long id4 = idGenerator4.nextId();

            // 验证同一个数据中心、工作机器下生成的ID是有序的
            if (id1 > lastId1) {
                lastId1 = id1;
            } else {
                Assertions.fail();
            }

            if (id2 > lastId2) {
                lastId2 = id2;
            } else {
                Assertions.fail();
            }

            if (id3 > lastId3) {
                lastId3 = id3;
            } else {
                Assertions.fail();
            }

            if (id4 > lastId4) {
                lastId4 = id4;
            } else {
                Assertions.fail();
            }

            // 等待1毫秒，确保不同毫秒生成的ID也是有序的
//            Thread.sleep(1);
        }
    }
}

