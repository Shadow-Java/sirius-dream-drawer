package sirius.core.concurrency.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author shadow
 * @date 2023/6/7 16:39
 * @since 1.0
 */

@BenchmarkMode(Mode.AverageTime)
// 将Fork设置为0
/**
 * 虽然Java支持多线程，但是不支持多进程，这就导致了所有的代码都在一个进程中运行，
 * 相同的代码在不同时刻的执行可能会引入前一阶段对进程profiler的优化，
 * 甚至会混入其他代码profiler优化时的参数，这很有可能会导致我们所编写的微基准测试出现不准确的问题
 */
@Fork(0)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample17 {
    // Inc1 和Inc2的实现完全一样
    interface Inc {
        int inc();
    }

    public static class Inc1 implements Inc {

        private int i = 0;

        @Override
        public int inc() {
            return ++i;
        }
    }

    public static class Inc2 implements Inc {

        private int i = 0;

        @Override
        public int inc() {
            return ++i;
        }
    }

    private Inc inc1 = new Inc1();
    private Inc inc2 = new Inc2();

    private int measure(Inc inc) {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            result += inc.inc();
        }
        return result;
    }

    @Benchmark
    public int measure_inc_1() {
        return this.measure(inc1);
    }

    @Benchmark
    public int measure_inc_2() {
        return this.measure(inc2);
    }

    @Benchmark
    public int measure_inc_3() {
        return this.measure(inc1);
    }

    //Fork设置为0，每一个基准测试方法都将会与JMHExample17使用同一个JVM进程，因此基准测试方法可能会混入JMHExample17进程的Profiler

    /**
     * measure_inc_1和measure_inc_2的实现方式几乎是一致的，它们的性能却存在着较大的差距，
     * 虽然measure_inc_1和measure_inc_3的代码实现完全相同，但还是存在着不同的性能数据，
     * 这其实就是JVM Profiler-guided optimizations导致的，由于我们所有的基准测试方法都与JMHExample17的JVM进程共享，
     * 因此难免在其中混入JMHExample17进程的Profiler，
     * 但是在将Fork设置为1的时候，也就是说每一次运行基准测试时都会开辟一个全新的JVM进程对其进行测试，那么多个基准测试之间将不会再存在干扰
     *
     * 若设置为1则会为每一个基准测试方法开辟新的进程去运行，当然，你可以将Fork设置为大于1的数值，那么它将多次运行在不同的进程中，不过一般情况下，我们只需要将Fork设置为1即可
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args)
            throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample17.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
