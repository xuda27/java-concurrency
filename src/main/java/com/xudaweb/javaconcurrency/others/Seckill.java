package com.xudaweb.javaconcurrency.others;

import java.util.concurrent.atomic.AtomicInteger;

public class Seckill {

    private static AtomicInteger stock = new AtomicInteger(10);

    public static synchronized boolean seckill() {
        if (stock.get() > 0) {
            System.out.println(Thread.currentThread().getName() + " 抢购成功！");
            stock.decrementAndGet();
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " 抢购失败！");
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean result = Seckill.seckill();
                    if (result) {
                        // 进行后续操作
                    }
                }
            }, "用户" + i).start();
        }
    }
}
