package sirius.core.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shadow
 * @create 2023-06-04 22:02
 **/
//什么时候使用多线程？当服务有复杂的计算，且不需要让主线程等待结果
public class SynchronizedVsLockVsAtomicInteger {

    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);
        System.out.println("当前start线程数："+Thread.activeCount());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for(int i = 0;i < 1000;i++){
            int finalI = i;
            Thread t = new Thread(()-> {
                int val = atomicInteger.incrementAndGet();
                if (val < 100){
                    System.out.println("thread-"+ finalI +":"+val);
                }else {
                    Thread.currentThread().interrupt();
                }
            },"thread-"+i);
            t.start();
        }
        System.out.println("线程："+Thread.activeCount());
    }

}
