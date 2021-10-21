package com.xudaweb.javaconcurrency.before5;


/**
 * Java编程Thread/Runnable（Java 5之前）
 * 
 * @author 徐达
 * @date 2017年10月26日
 */
public class ThreadMain {
	public static void main(String[] args) {
		
//		AtomicBoolean 属于 Java 5
		
		//子线程
		Thread thread  = new Thread(new Runnable() {
			
			/**
			 * synchronized 编程语言修饰符（锁）
			 */
			@Override
			public synchronized void run() {
				System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
			}
		}, "Sub");
		
		thread.start();


		new Thread(() ->
			System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName())
		).start();
		System.out.printf("[Thread：%s]Starting...\n", Thread.currentThread().getId());
		
		//输出：执行顺序重排序
		/**
		 * Starting...
		 * Hello,World
		 */
	}
}
