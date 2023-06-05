package sirius.core.concurrency.jmh;

/**
 * cpu核心数：https://zhuanlan.zhihu.com/p/86855590
 * @author shadow
 * @create 2023-06-04 23:18
 **/
public class Solution {

    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);

        for(int i = 0;i < availableProcessors;i++){
            Thread t = new Thread(()-> {
                while (true){
                    try {
                        int ps = Runtime.getRuntime().availableProcessors();
                        Thread.sleep(1L);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            t.start();
        }
    }

}
