package top.academy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class EvenOddBenchmark {

    @Param({"100000", "1000000"})
    private int size;

    private List<Integer> dataList;
    private int[] dataArray;

    @Setup(Level.Iteration)
    public void setup() {
        Random r = new Random();
        dataList = r.ints(size, 0, 101).boxed().toList();
        dataArray = r.ints(size, 0, 101).toArray();
    }

    @Benchmark
    public int[] testArrayQuick() {
        return EvenOddArrayQuick.sort(dataArray);
    }

    @Benchmark
    public int[] testArray() {
        return EvenOddArraySorter.sort(dataArray);
    }

    @Benchmark
    public List<Integer> testArrayList() {
        return EvenOddAlSorter.sort(dataList);
    }

    @Benchmark
    public List<Integer> testStream() {
        return EvenOddStreamSorter.sort(dataList);
    }

    @Benchmark
    public List<Integer> testParallelStream() {
        return EvenOddPStreamSorter.sort(dataList);
    }

    @Benchmark
    public List<Integer> testCompletableFuture() throws Exception {
        return EvenOddCFutureSorter.sort(dataList);
    }

    @Benchmark
    public List<Integer> testForkJoin() {
        return EvenOddFJSorter.sort(dataList);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EvenOddBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}