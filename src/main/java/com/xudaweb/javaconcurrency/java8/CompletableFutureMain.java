package com.xudaweb.javaconcurrency.java8;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * {@link CompletableFuture} Demo
 *
 * @author 徐达
 * @date 2017年10月27日
 * @see CompletionStage
 * @see CompletableFuture
 */
public class CompletableFutureMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		
//		//1.完成操作(可以被其他线程去做)
//		completableFuture.complete("Hello World");
//		String value = completableFuture.get();
//		System.out.println(value);
		
		//2.异步操作,阻塞执行
		//等价于new Runnable(){}
//		CompletableFuture asyncCompletableFuture = CompletableFuture.runAsync(()->{
//			System.out.println("Hello, World");
//		});
//		
//		//仍然是阻塞操作
//		asyncCompletableFuture.get();
//		
//		System.out.println("Starting...");
		
		//3.异步操作
//		CompletableFuture asyncCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
//
//			@Override
//			public String get() {
//				return "Hello,World";
//			}
//			
//		});
		
//		CompletableFuture<String> supplyAsyncCompletableFuture = CompletableFuture.supplyAsync(()-> {
//			//获取数据操作，假设来自于数据库
//			return String.format("[Thread：%s]Hello,World...\n", Thread.currentThread().getName());
//		});
//		String value = supplyAsyncCompletableFuture.get();
//		System.out.println("value=" + value);
//		System.out.println("Starting...");
		
		//4.合并操作
		//数据库操作费时，阻塞主线程
		//RT responseTime QPS/TPS
		//异步的特点，不要求立马返回
		// WebFlux 异步的web 提高吞吐量
		CompletableFuture combinedCompletableFuture = CompletableFuture.supplyAsync(() -> {
			//获取数据操作，假设来自于数据库
			return String.format("[Thread：%s]Hello,World...", Thread.currentThread().getName());
		}).thenApply(value -> {
			return value + "来自于数据库";
		}).thenApply(value -> {
			return value + LocalDate.now();
		}).thenApply(value -> {
			return value;
		}).thenRun(() -> {
			System.out.println("操作结束");
			//commit
		}); //reactive -> flatMap
		
		while (!combinedCompletableFuture.isDone()) {
		}
		System.out.println("Starting...");
	}

}
