package sirius.core.concurrency.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
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

import static java.lang.Math.PI;
import static java.lang.Math.log;

/**
 * @author shadow
 * @date 2023/6/7 11:48
 * @since 1.0
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample12 {

    @CompilerControl(CompilerControl.Mode.EXCLUDE)//禁止jvm优化
    @Benchmark
    public void test1() {
    }

    @Benchmark
    public void test2() {
        log(PI);
    }

    public static void main(String[] args) throws RunnerException {

        final Options opts = new OptionsBuilder()
                .include(JMHExample12.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
