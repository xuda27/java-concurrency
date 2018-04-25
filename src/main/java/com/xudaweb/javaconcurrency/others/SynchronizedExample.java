package com.xudaweb.javaconcurrency.others;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * synchronized 测试
 * 搭配Test
 */
public class SynchronizedExample {
    private static volatile SynchronizedExample instance = null;

    public static SynchronizedExample newInstance() {
        synchronized(SynchronizedExample.class){
            if (instance == null)
                instance = new SynchronizedExample();
        }
        return instance;
    }


    public void fun() {
        synchronized (this) {
            for(int i = 0; i < 100; i++) {
                System.out.print(i + " ");
            }
        }
    }

    public static void main(String[] args) {
        //两个线程进入同一个对象，一个线程等待另一个线程完成
        SynchronizedExample se = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> se.fun());
        executorService.execute(() -> se.fun());

        //两个线程进入不同的对象，互不影响。
//        SynchronizedExample se1 = new SynchronizedExample();
//        SynchronizedExample se2 = new SynchronizedExample();
//        ExecutorService executorService1 = Executors.newCachedThreadPool();
//        executorService1.execute(() -> se1.fun());
//        executorService1.execute(() -> se2.fun());
    }
}
