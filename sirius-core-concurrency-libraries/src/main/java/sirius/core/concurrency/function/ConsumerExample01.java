package sirius.core.concurrency.function;

import java.util.function.Consumer;

/**
 * 函数式接口@FunctionalInterface：只包含一个抽象方法的接口，称为函数式接口；
 * 箭头将Lambda表达式分为左右两部分，左边写的是实现的这个接口中的抽象方法中的形参列表，右边就是对抽象方法的处理；
 *
 * 函数式接口一般没有实现方法，即功能需要自己定义；Lambda表达式就是对函数式接口的一种简写方式，所以只有是函数式接口，我们才能用Lambda表达式；
 * @author shadow
 * @create 2023-06-11 19:04
 **/
public class ConsumerExample01 {

    public static void main(String[] args) {
        testConsumer();
        testAndThen();
    }

    /**
     * 一个简单的平方计算
     */
    public static void testConsumer() {
        //设置好Consumer实现方法
        Consumer<Integer> square = x -> System.out.println("平方计算 : " + x * x);
        //传入值
        square.accept(2);
    }
    /**
     * 定义3个Consumer并按顺序进行调用andThen方法
     */
    public static void testAndThen() {
        //当前值
        Consumer<Integer> consumer1 = x -> System.out.println("当前值 : " + x);
        //相加
        Consumer<Integer> consumer2 = x -> { System.out.println("相加 : " + (x + x)); };
        //相乘
        Consumer<Integer> consumer3 = x -> System.out.println("相乘 : " + x * x);
        //andThen拼接
        consumer1.andThen(consumer2).andThen(consumer3).accept(1);
    }

}
