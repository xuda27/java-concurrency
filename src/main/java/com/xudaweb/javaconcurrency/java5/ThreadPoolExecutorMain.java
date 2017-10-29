package com.xudaweb.javaconcurrency.java5;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor demo
 * 
 * @author 徐达
 * @date 2017年10月27日
 */
public class ThreadPoolExecutorMain {
	//通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
	public static void main(String[] args) {
		ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()); //执行队列是针对任务的缓存，任务在提交至线程池时，都会压入到执行队列中。
		try {
			singleThreadPool.execute(() -> {
				System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
			});
		} finally {
			singleThreadPool.shutdown();
		}


	}

}
