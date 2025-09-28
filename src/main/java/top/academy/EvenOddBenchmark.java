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
@Warmup(iterations = 1)
@Measurement(iterations = 3)
public class EvenOddBenchmark {

    @Param({"1000000"})
    private int size;

    private List<Integer> dataList;
    private int[] dataArray;

    @Setup(Level.Iteration)
    public void setup() {
        var r = new Random();
        int max = 5000;
        dataList = r.ints(size, 0, max).boxed().toList();
        // те же самые значения
        dataArray = dataList.stream().mapToInt(Integer::intValue).toArray();
        // или сгенерировать заново проще синтаксис
        // dataArray = r.ints(size, 0, max).toArray();
    }

    @Benchmark
    public int[] arrayQuick() { return EOArrayQuick.sort(dataArray); }
    @Benchmark
    public int[] arrayOptimized() { return EOArrayOptimized.sort(dataArray); }
    @Benchmark
    public int[] arrayParallelSort() {
        return EOArrayParallelSort.sort(dataArray);
    }
    @Benchmark
    public List<Integer> list() {
        return EOList.sort(dataList);
    }
    @Benchmark
    public List<Integer> stream() {
        return EOStream.sort(dataList);
    }
    @Benchmark
    public List<Integer> pStream() {
        return EOPStream.sort(dataList);
    }
    @Benchmark
    public List<Integer> completableFuture() throws Exception { return EOCompletableFuture.sort(dataList); }
    @Benchmark
    public List<Integer> forkRecursiveTask() {
        return EOForkRecursiveTask.sort(dataList);
    }
    @Benchmark
    public List<Integer> forkJoinPool() {
        return EOForkJoinPool.sort(dataList);
    }
    @Benchmark
    public List<Integer> structuredTaskScope() throws Exception {
        return EOStructuredConcurrency.sort(dataList);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EvenOddBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}