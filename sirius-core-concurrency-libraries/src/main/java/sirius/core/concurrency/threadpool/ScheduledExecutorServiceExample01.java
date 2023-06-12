package sirius.core.concurrency.threadpool;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author shadow
 * @create 2023-06-13 00:36
 **/
public class ScheduledExecutorServiceExample01 {


    public void example01() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        }, 1000, 60 * 1000);// 根据固定周期来执行TimerTask
    }

    public void example02() throws ExecutionException, InterruptedException {
        // 定义ScheduledThreadPoolExecutor，指定核心线程数为2，其他参数保持默认
        ScheduledThreadPoolExecutor scheduleExecutor = new ScheduledThreadPoolExecutor(2);

        // 延迟执行任务callable
        ScheduledFuture<String> future = scheduleExecutor.schedule(() -> {
            System.out.println("I am running");
            // 返回结果
            return "Hello";
        }, 10, TimeUnit.SECONDS); // 任务延迟10秒被执行
        System.out.println("result: " + future.get());
    }

    public void example03(){
        //runnable接口不关注任务执行结果
        Runnable command = () ->
        {
            // 获取当前时间
            long startTimestamp = System.currentTimeMillis();
            // 输出当前时间
            System.out.println("current timestamp: " + startTimestamp);
            // 随机休眠0～100毫秒
            try
            {
                TimeUnit.MILLISECONDS.sleep(current().nextInt(100));
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            // 输出任务执行耗时毫秒数
            System.out.println("elapsed time: "
                    + (System.currentTimeMillis() - startTimestamp));
        };
        // 定义ScheduledThreadPoolExecutor，指定核心线程数为2，其他参数保持默认
        ScheduledThreadPoolExecutor scheduleExecutor = new ScheduledThreadPoolExecutor(2);
        scheduleExecutor.scheduleAtFixedRate(command, 10, 1000,
                TimeUnit.MILLISECONDS);
    }

}
