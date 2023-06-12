package sirius.core.concurrency.threadpool;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * @author shadow
 * @create 2023-06-13 00:20
 **/
public class ThreadPoolExecutorExample01 {

    private ThreadPoolExecutor executor;


    @Setup(Level.Iteration)
    public void setUp() {
        // ① 创建ThreadPoolExecutor，7个构造参数
        //当阻塞队列还有空闲时，最多只有核心线程数，如果阻塞队列已满则会新建线程，但不会超过最大线程数，如果最大线程数已满则会实行拒绝策略
        executor = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());

        // ② 提交执行异步任务，不关注返回值
        executor.execute(() -> System.out.println(" execute the runnable task"));

        // ③ 提交执行异步任务，关注返回值
        Future<String> future = executor.submit(() -> " Execute the callable task and this is the result");

        // ④获取并输出callable任务的返回值
        //System.out.println(future.get());
    }

    public void example01() {
        // 新建线程池（本节中的线程池都将使用一致的构造参数）
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());

        // 当前线程池中并没有运行的线程
        assert executor.getActiveCount() == 0;
        assert executor.getMaximumPoolSize() == 4;
        assert executor.getCorePoolSize() == 2;

        // 提交任务并执行
        executor.execute(() -> System.out.println("print task"));

        // 当前线程池中有了一个运行线程，只不过该线程目前处于空闲状态
        assert executor.getActiveCount() == 1;
        assert executor.getMaximumPoolSize() == 4;
        assert executor.getCorePoolSize() == 2;
    }

    public void example02() {
        // 线程池会立即创建线程并执行任务
        executor.execute(() ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task finish done.");
        });
        assert executor.getActiveCount() == 1;
        assert executor.getQueue().isEmpty();
        executor.shutdown();
    }

    public void example03() {
        // 在线程池中执行12个任务
        for (int i = 0; i < 12; i++) {
            executor.execute(() ->
            {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task finish done by " + currentThread());
            });
        }
    }

}
