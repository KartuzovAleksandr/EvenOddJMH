package top.academy;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class EvenOddCFutureSorter {

    public static List<Integer> sort(List<Integer> numbers) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<List<Integer>> evenFuture = CompletableFuture.supplyAsync(() -> 
            numbers.stream()
                   .filter(x -> x % 2 == 0)
                   .sorted()
                   .toList(),
            executor
        );

        CompletableFuture<List<Integer>> oddFuture = CompletableFuture.supplyAsync(() ->
            numbers.stream()
                   .filter(x -> x % 2 != 0)
                   .sorted(Collections.reverseOrder())
                   .toList(),
            executor
        );

        List<Integer> result = Stream.concat(
                evenFuture.get().stream(),
                oddFuture.get().stream()
        ).toList();

        executor.shutdown();
        return result;
    }
}