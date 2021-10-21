package com.xudaweb.javaconcurrency.others;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuda
 */
public class ExecutorDemo {


    public static void main(String[] args) {
        //执行器，线程池（ThreadPoolExecutor）是它的一种实现
        //线程池是线程复用

        //固定长度线程池
        //好处：线程数量固定，不会存在线程重复初始化
        //坏处：没有对队列大小进行限制，线程初始化后，再也不能回收线程资源
        Executor executor = Executors.newFixedThreadPool(10);

        //CachedThreadPool 执行线程不固定，
        //好处：可以把新增任务全部缓存在一起，
        //坏处：只能用在短时间完成的任务（占用时间较长的操作可以导致线程数无限增大，系统资源耗尽）
//		Executor executor = Executors.newCachedThreadPool();

        //单线程线程池
        //好处：针对单cpu，单线程避免系统资源的抢夺
        //坏处：多cpu多线程时，不能完全利用cpu资源
//		Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
//            HttpClientUtil.doPost("http://localhost:7001/invoke/startProcessInstance");
            System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
        });

        //合理地关闭线程池是非常重要的
        if (executor instanceof ExecutorService) {
            ExecutorService executorService = ExecutorService.class.cast(executor);
            //当调用ExecutorService.shutdown方法的时候，线程池不再接收任何新任务，但此时线程池并不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
            executorService.shutdown();
            // 判断线程是否全部完成
            while (true) {
                if (executorService.isTerminated()) {
                    System.out.println("所有的子线程都结束了！");
                    break;
                }
            }
        }
    }
}
