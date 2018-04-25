package com.xudaweb.javaconcurrency.others;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 100; i++) {
                System.out.print(i + " ");
            }
        } finally {
            lock.unlock(); // 确保释放锁，从而避免发生死锁。
        }
    }

    public static void main(String[] args) {
//        LockExample le = new LockExample();
//        Executor executor = Executors.newCachedThreadPool();
//        executor.execute(()->le.func());
//        executor.execute(()->le.func());

        LockExample le1 = new LockExample();
        LockExample le2 = new LockExample();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->le1.func());
        executor.execute(()->le2.func());


    }
}
