package sirius.core.concurrency.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author shadow
 * @date 2023/6/7 11:24
 * @since 1.0
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 5个线程同时对共享资源进行操作
@Threads(5)
// 设置为线程间共享的资源
@State(Scope.Benchmark)
public class JMHExample09 {

    private Map<Long, Long> concurrentMap;
    private Map<Long, Long> synchronizedMap;

    // 关于Setup详见1.3.7节
    @Setup
    public void setUp() {
        concurrentMap = new ConcurrentHashMap<>();
        synchronizedMap = Collections.synchronizedMap(
                new HashMap<>());
    }

    @Benchmark
    public void testConcurrencyMap() {
        this.concurrentMap.put(System.nanoTime(),
                System.nanoTime());
    }

    @Benchmark
    public void testSynchronizedMap() {
        this.synchronizedMap.put(System.nanoTime(),
                System.nanoTime());
    }

    public static void main(String[] args)
            throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample09.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}