package sirius.core.concurrency.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author shadow
 * @date 2023/6/7 17:30
 * @since 1.0
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 设置为线程组共享的
@State(Scope.Group)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class JMHExample20 {

    private BlockingQueue<Integer> blockingQueue;

    private static final Integer VALUE = 100;

    @Setup
    public void setup(){
        // 初始化blockingQueue,指定容量为10
        blockingQueue = new ArrayBlockingQueue<>(10);
    }

    /**
     * 在线程组"test"中，有5个线程将不断地对blockingQueue进行提交数据
     * @return
     */
    @Benchmark
    @Group("test")
    @GroupThreads(5)
    public void get() throws InterruptedException {
        this.blockingQueue.put(VALUE);
    }

    /**
     * 在线程组"test"中，有5个线程将不断地对blockingQueue进行get数据
     * @return
     */
    @Benchmark
    @Group("test")
    @GroupThreads(5)
    public int add() throws InterruptedException {
        return this.blockingQueue.take();
    }

    /**
     * StackProfiler不仅可以输出线程堆栈的信息，还能统计程序在执行的过程中线程状态的数据，比如RUNNING状态、WAIT状态所占用的百分比等
     * GcProfiler可用于分析出在测试方法中垃圾回收器在JVM每个内存空间上所花费的时间
     * ClassLoaderProfiler可以帮助我们看到在基准方法的执行过程中有多少类被加载和卸载，但是考虑到在一个类加载器中同一个类只会被加载一次的情况，因此我们需要将Warmup设置为0，以避免在热身阶段就已经加载了基准测试方法所需的所有类
     * CompilerProfiler将会告诉你在代码的执行过程中JIT编译器所花费的优化时间，我们可以打开verbose模式观察更详细的输出。.verbosity(VerboseMode.EXTRA)
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHExample18.class.getSimpleName())
                // 设置10s超时
                .timeout(TimeValue.seconds(10))
                //  增加StackProfiler
                .addProfiler(StackProfiler.class)
                .build();
        new Runner(options).run();
    }

}
