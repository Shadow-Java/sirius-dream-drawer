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
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author shadow
 * @date 2023/6/7 11:21
 * @since 1.0
 */

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JMHExample08 {
    // 将Test设置为线程组共享的
    @State(Scope.Group)
    public static class Test {
        public Test() {
            System.out.println("create instance");
        }

        public void write() {
            System.out.println("write");
        }

        public void read() {
            System.out.println("read");
        }
    }

    // 在线程组"test"中，有三个线程将不断地对Test实例的write方法进行调用
    @GroupThreads(3)
    @Group("test")
    @Benchmark
    public void testWrite(Test test) {
        // 调用write方法
        test.write();
    }

    // 在线程组"test"中，有三个线程将不断地对Test实例的read方法进行调用
    @GroupThreads(3)
    @Group("test")
    @Benchmark
    public void testRead(Test test) {
        // 调用read方法
        test.read();
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample08.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}