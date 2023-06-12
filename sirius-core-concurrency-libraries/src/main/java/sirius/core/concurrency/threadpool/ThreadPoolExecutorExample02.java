package sirius.core.concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/**
 * @author shadow
 * @create 2023-06-13 00:33
 **/
public class ThreadPoolExecutorExample02 {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                // 使用自定义的ThreadFactory
                new MyThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 5; i++) {
            executor.execute(() ->
            {
                System.out.println("Task finish done by " + currentThread());
            });
        }
    }

    // 静态内部类，用于实现ThreadFactory接口
    private static class MyThreadFactory implements ThreadFactory {
        private final static String PREFIX = "ALEX";
        private final static AtomicInteger INC = new AtomicInteger();

        // 重写newThread方法
        @Override
        public Thread newThread(Runnable command) {
            // 定义线程组MyPool
            ThreadGroup group = new ThreadGroup("MyPool");
            // 构造线程时指定线程所属的线程组以及线程的命名
            Thread thread = new Thread(group, command, PREFIX + "-" + INC.getAndIncrement());
            // 设置线程优先级
            thread.setPriority(10);
            return thread;
        }
    }
}



