package com.xudaweb.javaconcurrency.java5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Callable是返回值操作，相对于Runnable
 * 
 * @author 徐达
 * @date 2017年10月27日
 * @see Runnable
 * @see Callable
 */
public class CallableMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//执行器服务，线程池（ThreadPoolExecutor）是它的一种实现
		//线程池是线程复用
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future<String> future = executorService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "[Thread：" + Thread.currentThread().getName() + "]Hello,World...\n";
			}
			
		});
		
		String value = future.get();
		
		System.out.println(value);
		
		//finally
		executorService.shutdown();
	}

}
