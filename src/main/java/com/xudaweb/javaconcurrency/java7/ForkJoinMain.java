package com.xudaweb.javaconcurrency.java7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 * Fork/Join 示例
 * 
 * @author 徐达
 * @date 2017年10月27日
 */
public class ForkJoinMain {

	public static void main(String[] args) {
		//并行：多个核心参与
		//并发：一同参与
		//ForkJoin 线程池
		System.out.printf("当前共用ForkJoin线程池并行级别：%d\n", ForkJoinPool.commonPool().getParallelism());
		System.out.printf("当前CPU处理器数：%d\n", Runtime.getRuntime().availableProcessors());
		
		// 与ThreadPoolExecutor相似
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
		forkJoinPool.invoke(new RecursiveAction() {
			
			@Override
			protected void compute() {
				System.out.println("[Thread：" + Thread.currentThread().getName() + "]Hello,World...\n");
			}
		});
		
		forkJoinPool.shutdown();
		
	}

}
