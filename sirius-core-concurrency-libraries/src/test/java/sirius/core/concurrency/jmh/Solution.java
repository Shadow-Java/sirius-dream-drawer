package sirius.core.concurrency.jmh;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * cpu核心数：https://zhuanlan.zhihu.com/p/86855590
 * @author shadow
 * @create 2023-06-04 23:18
 **/
public class Solution {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        blockingQueue.take();
    }

    public void getCurrentActiveThread(){
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("当前start线程数："+Thread.activeCount());

        for(int i = 0;i < availableProcessors;i++){
            Thread t = new Thread(()-> {
                while (true){
                    try {
                        int ps = Runtime.getRuntime().availableProcessors();
                        System.out.println("ps:"+ps);
                        Thread.sleep(1L);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"thread-"+i);

            t.start();
        }
        System.out.println("end:"+Thread.activeCount());
        Thread[] threads = new Thread[Thread.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread:threads){
            System.out.println("线程："+thread.getName());
        }
    }

}
