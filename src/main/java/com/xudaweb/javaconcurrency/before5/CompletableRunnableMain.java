package com.xudaweb.javaconcurrency.before5;

/**
 * 可完成的{@link Runnable}
 * 
 * @author 徐达
 * @date 2017年10月26日
 */
public class CompletableRunnableMain {

	public static void main(String[] args) throws InterruptedException {
		
		CompletedRunnable completedRunnable = new CompletedRunnable();
	
		Thread thread = new Thread(completedRunnable, "Sub");
		
		thread.start();
		
		//Waits for this thread to die.等着线程执行结束，串行操作，而不是并行操作！
		thread.join();
		
		System.out.printf("[Thread：%s]Starting...\n", Thread.currentThread().getId());
		
		System.out.printf("completedRunnable is completed:%s\n", completedRunnable.isCompleted()); //false
	}
	
	private static class CompletedRunnable implements Runnable {
		private volatile boolean completed = false;
		
		@Override
		public void run() {
			System.out.printf("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
			
			completed = true;
		}
		
		public boolean isCompleted() {
			return completed;
		}
		
	}

}
