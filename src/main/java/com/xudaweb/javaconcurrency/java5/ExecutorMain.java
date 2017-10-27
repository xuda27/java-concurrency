package com.xudaweb.javaconcurrency.java5;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor执行器
 * 
 * @author 徐达
 * @date 2017年10月27日
 */
public class ExecutorMain {
	
	public static void main(String[] args) {
		
		//执行器，线程池（ThreadPoolExecutor）是它的一种实现
		//线程池是线程复用
		Executor executor = Executors.newFixedThreadPool(1);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
			}
		});
		
		//合理地关闭线程池是非常重要的
		if (executor instanceof ExecutorService) {
			ExecutorService executorService = ExecutorService.class.cast(executor);
			executorService.shutdown();
		}
		
		//Java 5 开始实施 AutoCloseable,I/O,JDBC
	}
}
