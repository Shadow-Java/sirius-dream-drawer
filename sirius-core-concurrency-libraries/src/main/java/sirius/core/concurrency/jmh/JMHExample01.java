package sirius.core.concurrency.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author shadow
 * @create 2023-06-04 22:17
 **/
//AverageTime平均响应时间：每调用一次所耗费的时间，即elapsed time/operation
//Throughput（方法吞吐量):单位时间内可以对该方法调用多少次
//SampleTime（时间采样）采用一种抽样的方式来统计基准测试方法的性能结果,它会收集所有的性能数据，并且将其分布在不同的区间中
//SingleShotTime主要可用来进行冷测试，不论是Warmup还是Measurement，在每一个批次中基准测试方法只会被执行一次
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)//OutputTimeUnit提供了统计结果输出时的单位
//Thread独享的State:每一个运行基准测试方法的线程都会持有一个独立的对象实例，该实例既可能是作为基准测试方法参数传入的,将State设置为Scope.Thread一般主要是针对非线程安全的类
//Thread共享的State:需要测试在多线程的情况下某个类被不同线程操作时的性能,多线程访问某个共享数据时，我们需要让多个线程使用同一个实例才可以,因此JMH提供了多线程共享的一种状态Scope.Benchmark
//线程组共享的State:我们所编写的基准测试方法都会被JMH框架根据方法名的字典顺序排序后按照顺序逐个地调用执行，因此不存在两个方法同时运行的情况，如果想要测试某个共享数据或共享资源在多线程的情况下同时被读写的行为，是没有办法进行的，比如，在多线程高并发的环境中，多个线程同时对一个ConcurrentHashMap进行读写
//第一，是在多线程情况下的单个实例；第二，允许一个以上的基准测试方法并发并行地运行
@State(Scope.Thread)
@Measurement(iterations = 5)//度量5个批次
@Warmup(iterations = 3)
public class JMHExample01 {

    private final static String DATA = "DUMMY DATA";

    private List<String> arrayList;

    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp(){
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
    }

    @Benchmark
    public List<String> arrayListAdd(){
        this.arrayList.add(DATA);
        return arrayList;
    }

    @Benchmark
    public List<String> linkedListAdd(){
        this.linkedList.add(DATA);
        return this.linkedList;
    }

    /**
     * @warmup意为预热，在代码正式度量之前，先进行预热，将代码的执行经历早期优化、jvm运行期编译、jit优化之后的最终状态，从而获得真实性能
     * @Measurement则是在真正度量操作，每一轮的度量数据都会纳入统计之中
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(JMHExample01.class.getSimpleName())
                .forks(1)
                //设置全局参数 度量批次为5，在5个批次中，对基准方法的调用将会纳入统计
                .measurementIterations(10)
                //在度量之前会对代码进行三个批次的热身，使代码已经达到jvm的优化效果
                .warmupIterations(3)
                .build();
        new Runner(opts).run();
    }

}
