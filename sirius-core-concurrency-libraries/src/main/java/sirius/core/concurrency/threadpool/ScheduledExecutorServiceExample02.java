package sirius.core.concurrency.threadpool;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shadow
 * @create 2023-06-13 00:42
 **/
public class ScheduledExecutorServiceExample02 {

    public static void main(String[] args)
            throws InterruptedException {
        // 创建SingleThreadPool并执行任务
        singleThreadPool();
        // 输出当前JVM的线程堆栈信息
        printThreadStack();
        // 简单分割
        System.out.println("*************************************");
        // 显式调用GC，但是并不会立即作用（详见笔者第一本书中的ActiveObject）
        System.gc();
        TimeUnit.MINUTES.sleep(1);
        // 再次输出当前JVM的线程堆栈信息
        printThreadStack();
    }

    // 输出JVM线程堆栈信息
    private static void printThreadStack() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] ids = threadMXBean.getAllThreadIds();
        for (long id : ids) {
            System.out.println(threadMXBean.getThreadInfo(id));
        }
    }

    private static void singleThreadPool() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 提交执行异步任务
        executor.execute(() -> System.out.println("normal task."));
    }

}
