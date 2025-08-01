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

    @Param({"100000", "1000000"})
    private int size;

    private List<Integer> dataList;
    private int[] dataArray;

    @Setup(Level.Iteration)
    public void setup() {
        Random r = new Random();
        int max = 1000;
        dataList = r.ints(size, 0, max).boxed().toList();
        dataArray = r.ints(size, 0, max).toArray();
    }

    @Benchmark
    public int[] arrayQuick() {
        return EOArrayQuick.sort(dataArray);
    }

    @Benchmark
    public int[] arraySort() {
        return EOArraySort.sort(dataArray);
    }

    @Benchmark
    public List<Integer> arrayList() {
        return EOArrayList.sort(dataList);
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
    public List<Integer> completableFuture() throws Exception { return EOCFuture.sort(dataList); }
    @Benchmark
    public List<Integer> forkJoin() {
        return EOFJ.sort(dataList);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EvenOddBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}