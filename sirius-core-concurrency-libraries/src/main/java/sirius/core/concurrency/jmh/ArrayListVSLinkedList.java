package sirius.core.concurrency.jmh;

/**
 * @author shadow
 * @create 2023-06-04 22:43
 **/

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArrayListVSLinkedList {
    private final static String DATA = "DUMMY DATA";
    /**
     * 100万次
     */
    private final static int MAX_CAPACITY = 1_000_000;
    private final static int MAX_ITERATIONS = 10;

    /**
     * 测试不用的接口性能
     * @param list 上层接口
     */
    private static void test(List<String> list) {
        /**
         * 新增100万个数据
         */
        for (int i = 0; i < MAX_CAPACITY; i++) {
            list.add(DATA);
        }
    }

    /**
     * arrayList的性能
     * @param iterations 迭代次数
     */
    private static void arrayListPerfTest(int iterations) {
        for (int i = 0; i < iterations; i++) {
            final List<String> list = new ArrayList<>();
            //使用System.nanoTime()其时间源创建（并启动）新的Stopwatch
            final Stopwatch stopwatch = Stopwatch.createStarted();
            test(list);
            //用返回此Stopwatch经过时间，以所需的时间单位表示，并且向下取整  elapsed：以什么方式运行
            System.out.print(stopwatch.stop()
                    .elapsed(TimeUnit.MILLISECONDS)+"   ");
        }
    }

    /**
     * linkedList的性能
     * @param iterations 迭代次数
     */
    private static void linkedListPerfTest(int iterations) {
        for (int i = 0; i < iterations; i++) {
            /**
             * final int x = 10;
             * x = 20; // 编译错误，不能重新赋值
             *
             * 使用 final 关键字修饰的变量不能被重新赋值，但是它所引用的对象的状态是可以被修改的。同时，final 修饰的对象引用变量的地址也是可以被修改的。
             *
             * final Person p = new Person("Tom");
             * p = new Person("Jerry"); // 编译错误，不能重新赋值
             * p.setName("Mike"); // 可以修改对象的状态
             *
             * final主要是让"变量变为常量"，确保在声明后变量的地址不再被修改
             *
             * Java 中的 final 关键字用于修饰变量、方法和类，用于表示它们是不可变的或不可修改的。在变量中使用 final 关键字可以使变量成为常量，一旦被赋值就不能再次修改，从而提高程序的可读性、可维护性和安全性。
             *
             * 使用 final 关键字修饰变量的主要场景如下：
             *
             * 常量：将变量声明为 final 常量可以确保它们的值在声明后不会被修改。这样可以提高程序的可读性、可维护性和安全性，避免在程序执行过程中意外地修改常量的值。例如，Math.PI 就是一个常量，它的值是固定的，不会被修改。
             *
             * 多线程编程：在多线程编程中，使用 final 关键字可以确保变量在多个线程之间是可见的，从而避免线程安全问题。例如，在使用线程池时，如果将任务提交给线程池的时候使用了 final 修饰的变量，那么在任务执行的时候就不会出现线程安全问题。
             *
             * 优化性能：在某些情况下，使用 final 关键字可以帮助编译器进行优化，提高程序的性能。例如，将方法参数声明为 final 可以使编译器进行更好的优化，从而提高程序的执行效率。
             *
             * 需要注意的是，在使用 final 关键字时需要谨慎，因为它会限制变量的可修改性。如果变量需要在程序执行过程中被修改，那么就不应该使用 final 关键字。
             */
            final List<String> list = new LinkedList<>();
            final Stopwatch stopwatch = Stopwatch.createStarted();
            test(list);
            //用返回此Stopwatch经过时间，以所需的时间单位表示，并且向下取整
            System.out.print(stopwatch.stop()
                    .elapsed(TimeUnit.MILLISECONDS)+"  ");
        }
    }

    /**
     * 迭代10次，每次插入100万个数据
     *
     * 结论：linkedList的插入比arrayList的性能更好，但是这样的测试存在问题
     *
     * 其实是在Stopwatch内部记录了方法执行的开始纳秒数，这种操作本身会导致一些CPU时间的浪费
     *
     * VM可能会对其进行运行时的优化，比如循环展开、运行时编译等，这样会导致某组未经优化的性能数据参与统计计算
     * @param args
     */
    public static void main(String[] args) {
        arrayListPerfTest(MAX_ITERATIONS);
        System.out.println();
        System.out.println(Strings.repeat("#", 100));
        linkedListPerfTest(MAX_ITERATIONS);
    }
}
