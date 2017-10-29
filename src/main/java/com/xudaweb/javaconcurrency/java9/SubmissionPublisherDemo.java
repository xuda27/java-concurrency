package com.xudaweb.javaconcurrency.java9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

/**
 * 一个发布者多个订阅者
 * {@link SubmissionPublisher}
 *
 * @auther：XuDa
 * @date：2017/10/29
 */
public class SubmissionPublisherDemo {
	public static void main(String[] args) throws InterruptedException {
		//AutoCloseable 实现 即可 自动关闭
		try(SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>()) {
			publisher.subscribe(new IntegerSubscriber("A"));
			publisher.subscribe(new IntegerSubscriber("B"));
			publisher.subscribe(new IntegerSubscriber("C"));

			CompletableFuture completableFuture = publisher.consume(new Consumer<Integer>() {
				@Override
				public void accept(Integer value) {
					System.out.printf("Thread[:%s]:Current consume value：%s\n", Thread.currentThread().getName(), value);
				}
			}).thenRunAsync(()->{
				System.out.printf("异步Thread[:%s]\n", Thread.currentThread().getName());
			}).thenRun(() -> {
				System.out.printf("同步Thread[:%s]\n", Thread.currentThread().getName());
			});
			//提交数据各个订阅器
			publisher.submit(100);
			Thread.currentThread().join(1000L);
		}
//		try{
//
//		} finally {
//		//publisher.close();
//		}
	}

	private static class IntegerSubscriber implements Flow.Subscriber<Integer> {
		private final String name;
		private Flow.Subscription subscription;

		private IntegerSubscriber(String name) {
			this.name = name;
		}


		@Override
		public void onSubscribe(Flow.Subscription subscription) {
			System.out.printf("Thread[:%s]:Current Subscriber[%s] subscribes:%s\n", Thread.currentThread().getName(), name, subscription);
			subscription.request(1);
		}

		@Override
		public void onNext(Integer item) {
			System.out.printf("Thread[:%s]:Current Subscriber[%s] receive item[%d]\n", Thread.currentThread().getName(), name, item);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();

		}

		@Override
		public void onComplete() {
			System.out.printf("Thread[:%s]:Current Subscriber[%s] is completed\n", Thread.currentThread().getName(), name);

		}
	}
}
