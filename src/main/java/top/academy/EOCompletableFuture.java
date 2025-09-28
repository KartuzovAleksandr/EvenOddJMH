package top.academy;

import java.util.*;
import java.util.concurrent.*;

public class EOCompletableFuture {

    public static List<Integer> sort(List<Integer> numbers) throws Exception {
        int n = numbers.size();
        int nThreads = Runtime.getRuntime().availableProcessors();
        int chunkSize = (n + nThreads - 1) / nThreads;

        ExecutorService executor = ForkJoinPool.commonPool(); // используем общий пул

        List<CompletableFuture<List<List<Integer>>>> futures = new ArrayList<>();

        for (int i = 0; i < nThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, n);
            if (start >= n) break;

            List<Integer> chunk = numbers.subList(start, end);
            CompletableFuture<List<List<Integer>>> future = CompletableFuture.supplyAsync(() -> {
                List<Integer> evens = new ArrayList<>();
                List<Integer> odds = new ArrayList<>();
                for (int x : chunk) {
                    if ((x & 1) == 0) evens.add(x);
                    else odds.add(x);
                }
                return Arrays.asList(evens, odds);
            }, executor);

            futures.add(future);
        }

        List<Integer> allEvens = new ArrayList<>();
        List<Integer> allOdds = new ArrayList<>();

        for (var future : futures) {
            List<List<Integer>> pair = future.get();
            allEvens.addAll(pair.get(0));
            allOdds.addAll(pair.get(1));
        }

        // Используем общий пул, чтобы не создавать свой
        executor = ForkJoinPool.commonPool();
        CompletableFuture<Void> evenSort = CompletableFuture.runAsync(() ->
                allEvens.sort(null), executor
        );
        CompletableFuture<Void> oddSort = CompletableFuture.runAsync(() ->
                allOdds.sort(Collections.reverseOrder()), executor
        );
        // Ждём обе сортировки
        CompletableFuture.allOf(evenSort, oddSort).join();

        // Объединяем
        List<Integer> result = new ArrayList<>(allEvens.size() + allOdds.size());
        result.addAll(allEvens);
        result.addAll(allOdds);
        return result;
    }
}