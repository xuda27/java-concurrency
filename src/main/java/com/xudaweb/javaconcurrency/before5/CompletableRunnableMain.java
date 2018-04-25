package com.xudaweb.javaconcurrency.before5;

import java.util.HashMap;
import java.util.Map;

/**
 * 可完成的{@link Runnable}
 * 
 * @author 徐达
 * @date 2017年10月26日
 */
public class CompletableRunnableMain {
	private static int i = 0;

	public static void main(String[] args) throws InterruptedException {
		
		CompletedRunnable completedRunnable = new CompletedRunnable();
	
		Thread thread = new Thread(completedRunnable, "Sub");
		for (int j = 0; j < 10; j++)
			thread.start();
		
//		//Waits for this thread to die.等着线程执行结束，串行操作，而不是并行操作！
//		thread.join();
//
//		System.out.printf("[Thread：%s]Starting...\n", Thread.currentThread().getId());
//
//		System.out.printf("completedRunnable is completed:%s\n", completedRunnable.isCompleted()); //false
	}
	
	private static class CompletedRunnable implements Runnable {
		private volatile boolean completed = false;

		@Override
		public void run() {
			System.out.println(i);
			i++;
		}
		
		public boolean isCompleted() {
			return completed;
		}
		
	}

}
