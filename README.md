# Java并发编程
## Java 1.4 时代
  - 并发实现
      - Java Green Thread 绿色线程
      - Java Native Thread 
  - 编程模型
      - Thread
      - Runnable
  - 实现局限性
      - 缺少线程管理的原声支持（没有线程池）
      - 缺少执行完成的原声支持
      - 执行结果获取困难
      - Double Check Locking 不确定性（双检查锁，单例模式，线程安全）
      - 缺少”锁“API（线程安全）
## Java 5 时代
  - 并发框架
      - J.U.C = java.util.concurrent
  - 编程模型
      - Executor
      - Runnable、Callable
      - Future
## Java 7 时代
  - 并行框架
      - Fork/Join(参与、完成，递归的二分法)
  - 编程模型
      - ForkJoinPool
      - ForkJoinTask
      - RecursiveAction（递归）
  - Future的限制
      - 无法手动完成
      - 阻塞式结果返回
      - 无法链式多个Future
      - 无法合并多个Future结果
      - 缺少异常处理
## Java 8 时代
  - 异步并行框架
      - Fork/Join
  - 编程模型
      - CompletionStage
      - CompletableFuture（异步结果处理、链式处理、合并结果、异常处理）
