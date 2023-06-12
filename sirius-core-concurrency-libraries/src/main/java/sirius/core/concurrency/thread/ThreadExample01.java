package sirius.core.concurrency.thread;

/**
 * CPU 的逻辑线程数通常指的是处理器支持的同时执行的线程数，也称为超线程（Hyper-Threading）。超线程技术可以将一个物理处理器核心模拟为多个逻辑处理器核心，从而提高 CPU 的利用率，提高性能。
 * 例如，一台 CPU 支持 4 个物理核心和 8 个逻辑线程，那么就可以同时执行 8 个线程，其中每个物理核心可以模拟为 2 个逻辑核心。
 *
 * Java 中的线程则是指 Java 程序中的线程，是一种轻量级的执行单元，可以并发执行。
 * Java 的线程是由 JVM（Java Virtual Machine，Java 虚拟机）管理的，每个线程都有自己的执行栈、程序计数器和局部变量表等信息。
 * Java 线程的创建、启动、暂停、恢复和销毁等操作都由 Java 程序员通过 Java API 来实现。
 * 当 Java 程序中的线程数量超过 CPU 的逻辑线程数时，CPU 会采用时间分片（Time Slicing）技术，将 CPU 时间分配给不同的线程，以实现线程间的并发执行
 * @author shadow
 * @create 2023-06-11 18:46
 **/
public class ThreadExample01 {

    /**
     * 为什么使用join方法？<br/>
     * 在多线程编程中，经常会出现主线程执行完毕而子线程还在运行，这样子线程就会被强制结束；join方法就是阻塞当前主线程，等待子线程执行完毕才唤醒
     * @param args
     */
    public static void main(String[] args) {
        int count = 0;
        Thread thread = new Thread(()->{
            System.out.println("Thread t1 run");
        },"thread-1");

        thread.start();
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("count="+count);
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
