package com.xudaweb.javaconcurrency.java7;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;


/**
 * Fork/Join 1-10累加
 *
 * @author 徐达
 * @date 2017年10月27日
 */
public class ForkJoinAddMain {

	public static void main(String[] args) {
		//并行：多个核心参与
		//并发：一同参与
		//ForkJoin 线程池
		System.out.printf("当前共用ForkJoin线程池并行：%d\n", ForkJoinPool.commonPool().getParallelism());
		System.out.printf("当前CPU处理器数：%d\n", Runtime.getRuntime().availableProcessors());
		
		// 与ThreadPoolExecutor相似
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
		List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		//累加对象
		LongAdder longAdder = new LongAdder();
		// RecursiveAction递归操作
		AddTask addTask = new AddTask(nums, longAdder);
		forkJoinPool.invoke(addTask);
		forkJoinPool.shutdown();
		System.out.println(nums + "累加的结果：" + longAdder);
	}
	
	private static class AddTask extends RecursiveAction {
		//final线程安全
		private final List<Integer> nums;
		private final LongAdder longAdder;

		public AddTask(List<Integer> nums, LongAdder longAdder) {
			this.nums = nums;
			this.longAdder = longAdder;
		}

		@Override
		protected void compute() {
			int size = nums.size();
			
			if (size > 1) {
				//二分部分数量
				int parts = size / 2;
				//左半部
				List<Integer> leftPart = nums.subList(0, parts);
				AddTask leftTask = new AddTask(leftPart, longAdder);
				//右半部
				List<Integer> rightpart = nums.subList(parts, size);
				AddTask rightTask = new AddTask(rightpart, longAdder);
				
				invokeAll(leftTask, rightTask); //fork/join
			} else { //当前元素只有一个
				if (size == 0) { // 保护
					return;
				}
				
				Integer value = nums.get(0);
				//累加
				longAdder.add(value.longValue());
			}
		}
		
	}
}
